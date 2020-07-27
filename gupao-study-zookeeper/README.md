# 基本要素

## 集群中角色

Leader ,Follower .Observer

## 数据存储方式

Zookeeper 通过DataNode 来保存每个节点的信息，DataTree来保存整个目录的节点信息，通过ConcurrentHashMap实现目录节点的名称与数据的映射。

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

## 数据同步



## Leader选举



# 应用

Leader 选举，分布式锁，注册中心，配置中心

## 基于Curator的leader选举



## 基于Curator 的分布式错实现



## 基于Curator 自定义实现的分布式锁案例

`com.gupao.study.zookeeper.distribute.lock.DistributeLockTestDemo`

原理：

Zookeeper 临时有序节点的使用，通过比较目录节点下的 当前需要获取锁的线程创建的节点是否为最小值，如果为最小值则获取锁成功，执行完成业务程序后删除节点，来释放锁

