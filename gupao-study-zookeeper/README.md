# 基本要素

## 集群中角色

Leader ：所有的事务请求（增删改）必须由一个全局唯一的服务器协调处理，这样的服务器被称为leader服务器

Follower：除了节点之外的其他节点

Observer：leader选举中的角色

## 数据存储方式

Zookeeper 通过定义ZKDatabase，指向DataTree，DataTree来保存整个目录的节点信息，DataNode 来保存每个节点的信息，通过ConcurrentHashMap实现目录节点的名称与数据（DataNode）的映射。

```java
public class ZKDatabase {
    
    private static final Logger LOG = LoggerFactory.getLogger(ZKDatabase.class);
    
    /**
     * make sure on a clear you take care of 
     * all these members.
     */
    protected DataTree dataTree;
    protected ConcurrentHashMap<Long, Integer> sessionsWithTimeouts; // 这里保存客户端会话，以及会话过期时间
    protected FileTxnSnapLog snapLog;
    protected long minCommittedLog, maxCommittedLog;
    public static final int commitLogCount = 500;
    protected static int commitLogBuffer = 700;
    protected LinkedList<Proposal> committedLog = new LinkedList<Proposal>(); // leader事务提交日志
    protected ReentrantReadWriteLock logLock = new ReentrantReadWriteLock(); // 在进行follower同步时使用读锁
    volatile private boolean initialized = false;
 	// ....
 }
```



```java
public class DataTree {
    private static final Logger LOG = LoggerFactory.getLogger(DataTree.class);

    /**
     * This hashtable provides a fast lookup to the datanodes. The tree is the
     * source of truth and is where all the locking occurs
     */
    private final ConcurrentHashMap<String, DataNode> nodes =
        new ConcurrentHashMap<String, DataNode>();
	// 节点的监听，节点数据变化的时候会触发 WatchedEvent
    private final WatchManager dataWatches = new WatchManager();
	// 节点的监听，子节点变化的时候会触发 WatchedEvent
    private final WatchManager childWatches = new WatchManager();

    /** the root of zookeeper tree */
    private static final String rootZookeeper = "/";

    /** the zookeeper nodes that acts as the management and status node **/
    private static final String procZookeeper = Quotas.procZookeeper;

    /** this will be the string thats stored as a child of root */
    private static final String procChildZookeeper = procZookeeper.substring(1);

    /**
     * the zookeeper quota node that acts as the quota management node for
     * zookeeper
     */
    private static final String quotaZookeeper = Quotas.quotaZookeeper;

    /** this will be the string thats stored as a child of /zookeeper */
    private static final String quotaChildZookeeper = quotaZookeeper
            .substring(procZookeeper.length() + 1);

    /**
     * the path trie that keeps track fo the quota nodes in this datatree
     */
    private final PathTrie pTrie = new PathTrie();

    /**
     * This hashtable lists the paths of the ephemeral nodes of a session.
     */
    private final Map<Long, HashSet<String>> ephemerals =
        new ConcurrentHashMap<Long, HashSet<String>>();

    private final ReferenceCountedACLCache aclCache = new ReferenceCountedACLCache();
  // ...
 }
```



```java
public class DataNode implements Record {
    /** the parent of this datanode */
    DataNode parent;

    /** the data for this datanode */
    byte data[];

    /**
     * the acl map long for this datanode. the datatree has the map
     */
    Long acl;

    /**
     * the stat for this node that is persisted to disk.
     */
    public StatPersisted stat;

    /**
     * the list of children for this node. note that the list of children string
     * does not contain the parent path -- just the last part of the path. This
     * should be synchronized on except deserializing (for speed up issues).
     */
    private Set<String> children = null;
}
```



```java
public class Stat implements Record {
  private long czxid; // 创建节点的事物ID
  private long mzxid; // 修改节点的事物ID
  private long ctime; // 创建时间
  private long mtime;
  private int version;
  private int cversion; //这表示对此znode的子节点进行的更改次数
  private int aversion;
  private long ephemeralOwner; // 临时节点的会话ID
  private int dataLength; //数据长度
  private int numChildren; // 子节点个数
  private long pzxid; //子节点变更的事物ID
  public Stat() {
  }
}
```

## 数据节点类型

1. 持久化节点
2. 持久化有序节点 （有序自增的持久化节点）
3. 临时节点 （不可再有子节点，客户端断开连接会被删除，过期时间由客户端的ConnectTimeOut决定）
4. 临时有序节点（不可再有子节点，有序自增的临时节点）
5. 容器节点（Container 3.5.5新增）
6. TTL节点（3.5.5新增）

# 原理

## 数据同步(一致性协议—Zab协议)

1. 崩溃恢复

   当leader服务器出现崩溃退出或者机器重启，又或者集群中已经不存在过半的服务器与该leader服务器保持童心时，那么在将会开启崩溃恢复状态，来达到一个一致的状态，整个流程进入崩溃恢复模式

   * Leader选举

     要求：

     ​	1. ZAB协议需要确保那些已经在Leader服务器上已经提交的事务最终被所有的服务器都提交。

     ​	2. ZAB协议需要确保丢弃那些只在Leader服务器上被提出的事务

     ​	Leader选举算法必须满足这两点要求。

     策略：

     ​	在ZAB协议中，ZXID事务编号设计是64位的数字，其中低32位是一个事务ID的自增计数器，针对客户端的每一个事务请求，leader服务器在产生一个新的事务Proposal的时候，都会对该计数器进行加1操作，高32位则代表epoch编号（朝代，或者任职期），每当选举出一个leader服务器，会取出当前事务日志Proposal的最大值ZXID，并取出epoch对其进行加1，之后回作为最新的epoch，然后设置低32位为0（重置）。

   * 数据同步

     在新的Leader选举成功的过程已经决定了当前事务提交的最大值，实际上就包含包含了崩溃的leader事务提交的最大值，即leader提交的事务被所有的服务器都提交。

     崩溃的leader节点重启连接后epoch不如新的leader节点epoch高，因此该崩溃的leader节点未提交的事务无法完成同步（满足要求2）

2. 消息广播

   * 简化版的2PC 二阶段提交，过半节点提交成功即算事务执行成功

   * 整个消息广播协议基于具有FIFO特性的TCP协议来进行网络通信，能够确保消息广播过程中接收和发送的有序性

   * 过程：

     在消息广播过程中，leader服务器会为每一个Follower服务器分配一个单独的队列，然后将需要广播的事务Proposal依次放入到这些队列中，并且跟去FIFO策略进行消息发送，Follower节点请求发送的处理类`org.apache.zookeeper.server.quorum.LearnerHandler`；每一个Follower在接收到Proposal后会将其以事务日志的方式写入磁盘，并且在写入成功后反馈给Leader服务器一个Ack响应，当Leader服务器接收超过半数（默认 `QuorumMaj`）Follower（`QuorumHierarchical` 可以设置权重）的ACK响应后，就会广播一个Commit消息给所有的Follower服务器通知其事务进行提交

     ```java
     public class LearnerHandler extends ZooKeeperThread{
         /**
          * The packets to be sent to the learner
          */
         final LinkedBlockingQueue<QuorumPacket> queuedPackets =
             new LinkedBlockingQueue<QuorumPacket>();
         // ...
     }
     ```

     

   问题：无法处理leader服务器崩溃退出带来的不一致问题，因此需要崩溃恢复

## Leader选举



# 应用

Leader 选举，分布式锁，注册中心，配置中心

## 基于Curator的leader选举



## 基于Curator 的分布式错实现



## 基于Curator 自定义实现的分布式锁案例

`com.gupao.study.zookeeper.distribute.lock.DistributeLockTestDemo`

原理：

Zookeeper 临时有序节点的使用，通过比较目录节点下的 当前需要获取锁的线程创建的节点是否为最小值，如果为最小值则获取锁成功，执行完成业务程序后删除节点，来释放锁

