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

   ## Watcher 机制

   1. 存储：exists ， getData， setWatches，getChildren， getChildren2等操作会增加事件，最终存储在不同的Path 对应的 DataTree中，watcher 的实质是`ServerCnxn`连接。客户端在 setWatches 时会有三种watcher类型DataWatches，ExistWatches，ChildWatches ，服务端只保留两种类型的watcher （dataWatches，childWatches），existWatcher 只用来推送 `NodeCreated` 事件
   2. 触发：`org.apache.zookeeper.server.FinalRequestProcessor` 最终的处理类进行监听事件的发布

   ```java
   public class FinalRequestProcessor implements RequestProcessor {
   	public void processRequest(Request request) {
           if (LOG.isDebugEnabled()) {
               LOG.debug("Processing request:: " + request);
           }
           // request.addRQRec(">final");
           long traceMask = ZooTrace.CLIENT_REQUEST_TRACE_MASK;
           if (request.type == OpCode.ping) {
               traceMask = ZooTrace.SERVER_PING_TRACE_MASK;
           }
           if (LOG.isTraceEnabled()) {
               ZooTrace.logRequest(LOG, traceMask, 'E', request, "");
           }
           ProcessTxnResult rc = null;
           synchronized (zks.outstandingChanges) {
               while (!zks.outstandingChanges.isEmpty()
                       && zks.outstandingChanges.get(0).zxid <= request.zxid) {
                   ChangeRecord cr = zks.outstandingChanges.remove(0);
                   if (cr.zxid < request.zxid) {
                       LOG.warn("Zxid outstanding "
                               + cr.zxid
                               + " is less than current " + request.zxid);
                   }
                   if (zks.outstandingChangesForPath.get(cr.path) == cr) {
                       zks.outstandingChangesForPath.remove(cr.path);
                   }
               }
               if (request.hdr != null) {
                  TxnHeader hdr = request.hdr;
                  Record txn = request.txn;
   				// 这里处理请求时，发布Watcher事件
                   // 通过txn 发送WatcherEvent到客户端
                  rc = zks.processTxn(hdr, txn); 
               }
               // do not add non quorum packets to the queue.
               if (Request.isQuorum(request.type)) {
                   zks.getZKDatabase().addCommittedProposal(request);
               }
           }
   
           if (request.hdr != null && request.hdr.getType() == OpCode.closeSession) {
               ServerCnxnFactory scxn = zks.getServerCnxnFactory();
               // this might be possible since
               // we might just be playing diffs from the leader
               if (scxn != null && request.cnxn == null) {
                   // calling this if we have the cnxn results in the client's
                   // close session response being lost - we've already closed
                   // the session/socket here before we can send the closeSession
                   // in the switch block below
                   scxn.closeSession(request.sessionId);
                   return;
               }
           }
   
           if (request.cnxn == null) {
               return;
           }
           ServerCnxn cnxn = request.cnxn;
   
           String lastOp = "NA";
           zks.decInProcess();
           Code err = Code.OK;
           Record rsp = null;
           boolean closeSession = false;
           try {
               if (request.hdr != null && request.hdr.getType() == OpCode.error) {
                   throw KeeperException.create(KeeperException.Code.get((
                           (ErrorTxn) request.txn).getErr()));
               }
   
               KeeperException ke = request.getException();
               if (ke != null && request.type != OpCode.multi) {
                   throw ke;
               }
   
               if (LOG.isDebugEnabled()) {
                   LOG.debug("{}",request);
               }
               switch (request.type) {
               case OpCode.ping: {
                   zks.serverStats().updateLatency(request.createTime);
   
                   lastOp = "PING";
                   cnxn.updateStatsForResponse(request.cxid, request.zxid, lastOp,
                           request.createTime, Time.currentElapsedTime());
   
                   cnxn.sendResponse(new ReplyHeader(-2,
                           zks.getZKDatabase().getDataTreeLastProcessedZxid(), 0), null, "response");
                   return;
               }
               case OpCode.createSession: {
                   zks.serverStats().updateLatency(request.createTime);
   
                   lastOp = "SESS";
                   cnxn.updateStatsForResponse(request.cxid, request.zxid, lastOp,
                           request.createTime, Time.currentElapsedTime());
   
                   zks.finishSessionInit(request.cnxn, true);
                   return;
               }
               case OpCode.multi: {
                   lastOp = "MULT";
                   rsp = new MultiResponse() ;
   
                   for (ProcessTxnResult subTxnResult : rc.multiResult) {
   
                       OpResult subResult ;
   
                       switch (subTxnResult.type) {
                           case OpCode.check:
                               subResult = new CheckResult();
                               break;
                           case OpCode.create:
                               subResult = new CreateResult(subTxnResult.path);
                               break;
                           case OpCode.delete:
                               subResult = new DeleteResult();
                               break;
                           case OpCode.setData:
                               subResult = new SetDataResult(subTxnResult.stat);
                               break;
                           case OpCode.error:
                               subResult = new ErrorResult(subTxnResult.err) ;
                               break;
                           default:
                               throw new IOException("Invalid type of op");
                       }
   
                       ((MultiResponse)rsp).add(subResult);
                   }
   
                   break;
               }
               case OpCode.create: {
                   lastOp = "CREA";
                   rsp = new CreateResponse(rc.path);
                   err = Code.get(rc.err);
                   break;
               }
               case OpCode.delete: {
                   lastOp = "DELE";
                   err = Code.get(rc.err);
                   break;
               }
               case OpCode.setData: {
                   lastOp = "SETD";
                   rsp = new SetDataResponse(rc.stat);
                   err = Code.get(rc.err);
                   break;
               }
               case OpCode.setACL: {
                   lastOp = "SETA";
                   rsp = new SetACLResponse(rc.stat);
                   err = Code.get(rc.err);
                   break;
               }
               case OpCode.closeSession: {
                   lastOp = "CLOS";
                   closeSession = true;
                   err = Code.get(rc.err);
                   break;
               }
               case OpCode.sync: {
                   lastOp = "SYNC";
                   SyncRequest syncRequest = new SyncRequest();
                   ByteBufferInputStream.byteBuffer2Record(request.request,
                           syncRequest);
                   rsp = new SyncResponse(syncRequest.getPath());
                   break;
               }
               case OpCode.check: {
                   lastOp = "CHEC";
                   rsp = new SetDataResponse(rc.stat);
                   err = Code.get(rc.err);
                   break;
               }
               case OpCode.exists: {
                   lastOp = "EXIS";
                   // TODO we need to figure out the security requirement for this!
                   ExistsRequest existsRequest = new ExistsRequest();
                   ByteBufferInputStream.byteBuffer2Record(request.request,
                           existsRequest);
                   String path = existsRequest.getPath();
                   if (path.indexOf('\0') != -1) {
                       throw new KeeperException.BadArgumentsException();
                   }
                   Stat stat = zks.getZKDatabase().statNode(path, existsRequest
                           .getWatch() ? cnxn : null);
                   rsp = new ExistsResponse(stat);
                   break;
               }
               case OpCode.getData: {
                   lastOp = "GETD";
                   GetDataRequest getDataRequest = new GetDataRequest();
                   ByteBufferInputStream.byteBuffer2Record(request.request,
                           getDataRequest);
                   DataNode n = zks.getZKDatabase().getNode(getDataRequest.getPath());
                   if (n == null) {
                       throw new KeeperException.NoNodeException();
                   }
                   PrepRequestProcessor.checkACL(zks, zks.getZKDatabase().aclForNode(n),
                           ZooDefs.Perms.READ,
                           request.authInfo);
                   Stat stat = new Stat();
                   byte b[] = zks.getZKDatabase().getData(getDataRequest.getPath(), stat,
                           getDataRequest.getWatch() ? cnxn : null);
                   rsp = new GetDataResponse(b, stat);
                   break;
               }
               case OpCode.setWatches: {
                   lastOp = "SETW";
                   SetWatches setWatches = new SetWatches();
                   // XXX We really should NOT need this!!!!
                   request.request.rewind();
                   ByteBufferInputStream.byteBuffer2Record(request.request, setWatches);
                   long relativeZxid = setWatches.getRelativeZxid();
                   // 客户端设置watcher
                   zks.getZKDatabase().setWatches(relativeZxid, 
                           setWatches.getDataWatches(), 
                           setWatches.getExistWatches(),
                           setWatches.getChildWatches(), cnxn);
                   break;
               }
               case OpCode.getACL: {
                   lastOp = "GETA";
                   GetACLRequest getACLRequest = new GetACLRequest();
                   ByteBufferInputStream.byteBuffer2Record(request.request,
                           getACLRequest);
                   Stat stat = new Stat();
                   List<ACL> acl = 
                       zks.getZKDatabase().getACL(getACLRequest.getPath(), stat);
                   rsp = new GetACLResponse(acl, stat);
                   break;
               }
               case OpCode.getChildren: {
                   lastOp = "GETC";
                   GetChildrenRequest getChildrenRequest = new GetChildrenRequest();
                   ByteBufferInputStream.byteBuffer2Record(request.request,
                           getChildrenRequest);
                   DataNode n = zks.getZKDatabase().getNode(getChildrenRequest.getPath());
                   if (n == null) {
                       throw new KeeperException.NoNodeException();
                   }
                   PrepRequestProcessor.checkACL(zks, zks.getZKDatabase().aclForNode(n),
                           ZooDefs.Perms.READ,
                           request.authInfo);
                   List<String> children = zks.getZKDatabase().getChildren(
                           getChildrenRequest.getPath(), null, getChildrenRequest
                                   .getWatch() ? cnxn : null);
                   rsp = new GetChildrenResponse(children);
                   break;
               }
               case OpCode.getChildren2: {
                   lastOp = "GETC";
                   GetChildren2Request getChildren2Request = new GetChildren2Request();
                   ByteBufferInputStream.byteBuffer2Record(request.request,
                           getChildren2Request);
                   Stat stat = new Stat();
                   DataNode n = zks.getZKDatabase().getNode(getChildren2Request.getPath());
                   if (n == null) {
                       throw new KeeperException.NoNodeException();
                   }
                   PrepRequestProcessor.checkACL(zks, zks.getZKDatabase().aclForNode(n),
                           ZooDefs.Perms.READ,
                           request.authInfo);
                   List<String> children = zks.getZKDatabase().getChildren(
                           getChildren2Request.getPath(), stat, getChildren2Request
                                   .getWatch() ? cnxn : null);
                   rsp = new GetChildren2Response(children, stat);
                   break;
               }
               }
           } catch (SessionMovedException e) {
               // session moved is a connection level error, we need to tear
               // down the connection otw ZOOKEEPER-710 might happen
               // ie client on slow follower starts to renew session, fails
               // before this completes, then tries the fast follower (leader)
               // and is successful, however the initial renew is then 
               // successfully fwd/processed by the leader and as a result
               // the client and leader disagree on where the client is most
               // recently attached (and therefore invalid SESSION MOVED generated)
               cnxn.sendCloseSession();
               return;
           } catch (KeeperException e) {
               err = e.code();
           } catch (Exception e) {
               // log at error level as we are returning a marshalling
               // error to the user
               LOG.error("Failed to process " + request, e);
               StringBuilder sb = new StringBuilder();
               ByteBuffer bb = request.request;
               bb.rewind();
               while (bb.hasRemaining()) {
                   sb.append(Integer.toHexString(bb.get() & 0xff));
               }
               LOG.error("Dumping request buffer: 0x" + sb.toString());
               err = Code.MARSHALLINGERROR;
           }
   
           long lastZxid = zks.getZKDatabase().getDataTreeLastProcessedZxid();
           ReplyHeader hdr =
               new ReplyHeader(request.cxid, lastZxid, err.intValue());
   
           zks.serverStats().updateLatency(request.createTime);
           cnxn.updateStatsForResponse(request.cxid, lastZxid, lastOp,
                       request.createTime, Time.currentElapsedTime());
   
           try {
               cnxn.sendResponse(hdr, rsp, "response");
               if (closeSession) {
                   cnxn.sendCloseSession();
               }
           } catch (IOException e) {
               LOG.error("FIXMSG",e);
           }
       }
   }
   ```

   

   

   ## 客户端请求处理

   Leader 和Follower 节点都遵循以下步骤启动请求处理程序

   ```java
   public class ZooKeeperServer implements SessionExpirer, ServerStats.Provider {
   	// zookeeper 服务请求启动步骤
   	public synchronized void startup() {
           if (sessionTracker == null) {
               createSessionTracker(); // 创建session的监听
           }
           startSessionTracker();
           setupRequestProcessors();
   
           registerJMX();
   
           setState(State.RUNNING);
           notifyAll();
       }	
       // ... ...
   }
   ```

   1. Leader 节点客户端请求处理

      ```java
      public class LeaderZooKeeperServer extends QuorumZooKeeperServer {
          // ... ...
          // 责任链异步处理
      	@Override
          protected void setupRequestProcessors() {
              RequestProcessor finalProcessor = new FinalRequestProcessor(this);
              RequestProcessor toBeAppliedProcessor = new Leader.ToBeAppliedRequestProcessor(
                      finalProcessor, getLeader().toBeApplied);
              commitProcessor = new CommitProcessor(toBeAppliedProcessor,
                      Long.toString(getServerId()), false,
                      getZooKeeperServerListener());
              commitProcessor.start();
              ProposalRequestProcessor proposalProcessor = new ProposalRequestProcessor(this,
                      commitProcessor);
              proposalProcessor.initialize();
              firstProcessor = new PrepRequestProcessor(this, proposalProcessor);
              ((PrepRequestProcessor)firstProcessor).start();
          }
      
          // ... ...
      }
      ```

      

   2. Follower 节点客户端请求处理

      ```java
      public class FollowerZooKeeperServer extends LearnerZooKeeperServer {
          // ... ...
          // 责任链异步处理
      	@Override
          protected void setupRequestProcessors() {
              RequestProcessor finalProcessor = new FinalRequestProcessor(this);
              commitProcessor = new CommitProcessor(finalProcessor,
                      Long.toString(getServerId()), true,
                      getZooKeeperServerListener());
              commitProcessor.start();
              firstProcessor = new FollowerRequestProcessor(this, commitProcessor);
              ((FollowerRequestProcessor) firstProcessor).start();
              syncProcessor = new SyncRequestProcessor(this,
                      new SendAckRequestProcessor((Learner)getFollower()));
              syncProcessor.start();
          }
          
           // ... ...
      }
      ```

      



# 源码分析—流程分析

1. Zookeeper 集群通过`org.apache.zookeeper.server.quorum.QuorumPeerMain` 启动，每一个集群中的节点被称为QuorumPeer

2. 启动流程：

   ```java
   //  QuorumPeer 本质是一个线程
   public class QuorumPeer extends ZooKeeperThread implements QuorumStats.Provider {
       // 通信服务工厂
       // 1. org.apache.zookeeper.server.NIOServerCnxnFactory
       // 2. org.apache.zookeeper.server.NettyServerCnxnFactory
       ServerCnxnFactory cnxnFactory;
    	// ... ...
    	@Override
       public synchronized void start() {
           loadDataBase(); // 加载本地数据
           cnxnFactory.start(); // 暴露对外服务端口       
           startLeaderElection(); // Leader 选举
           super.start(); // 这里执行run()方法
       }
       // ... ...
       @Override
       public void run() {
           setName("QuorumPeer" + "[myid=" + getId() + "]" +
                   cnxnFactory.getLocalAddress());
   
           LOG.debug("Starting quorum peer");
           try {
               jmxQuorumBean = new QuorumBean(this);
               MBeanRegistry.getInstance().register(jmxQuorumBean, null);
               for(QuorumServer s: getView().values()){
                   ZKMBeanInfo p;
                   if (getId() == s.id) {
                       p = jmxLocalPeerBean = new LocalPeerBean(this);
                       try {
                           MBeanRegistry.getInstance().register(p, jmxQuorumBean);
                       } catch (Exception e) {
                           LOG.warn("Failed to register with JMX", e);
                           jmxLocalPeerBean = null;
                       }
                   } else {
                       p = new RemotePeerBean(s);
                       try {
                           MBeanRegistry.getInstance().register(p, jmxQuorumBean);
                       } catch (Exception e) {
                           LOG.warn("Failed to register with JMX", e);
                       }
                   }
               }
           } catch (Exception e) {
               LOG.warn("Failed to register with JMX", e);
               jmxQuorumBean = null;
           }
   
           try {
               /*
                * Main loop
                */
               while (running) {
                   switch (getPeerState()) {
                   case LOOKING:
                       LOG.info("LOOKING");
   
                       if (Boolean.getBoolean("readonlymode.enabled")) {
                           LOG.info("Attempting to start ReadOnlyZooKeeperServer");
   
                           // Create read-only server but don't start it immediately
                           final ReadOnlyZooKeeperServer roZk = new ReadOnlyZooKeeperServer(
                                   logFactory, this,
                                   new ZooKeeperServer.BasicDataTreeBuilder(),
                                   this.zkDb);
       
                           // Instead of starting roZk immediately, wait some grace
                           // period before we decide we're partitioned.
                           //
                           // Thread is used here because otherwise it would require
                           // changes in each of election strategy classes which is
                           // unnecessary code coupling.
                           Thread roZkMgr = new Thread() {
                               public void run() {
                                   try {
                                       // lower-bound grace period to 2 secs
                                       sleep(Math.max(2000, tickTime));
                                       if (ServerState.LOOKING.equals(getPeerState())) {
                                           roZk.startup();
                                       }
                                   } catch (InterruptedException e) {
                                       LOG.info("Interrupted while attempting to start ReadOnlyZooKeeperServer, not started");
                                   } catch (Exception e) {
                                       LOG.error("FAILED to start ReadOnlyZooKeeperServer", e);
                                   }
                               }
                           };
                           try {
                               roZkMgr.start();
                               setBCVote(null);
                               setCurrentVote(makeLEStrategy().lookForLeader());
                           } catch (Exception e) {
                               LOG.warn("Unexpected exception",e);
                               setPeerState(ServerState.LOOKING);
                           } finally {
                               // If the thread is in the the grace period, interrupt
                               // to come out of waiting.
                               roZkMgr.interrupt();
                               roZk.shutdown();
                           }
                       } else {
                           try {
                               setBCVote(null);
                               setCurrentVote(makeLEStrategy().lookForLeader());
                           } catch (Exception e) {
                               LOG.warn("Unexpected exception", e);
                               setPeerState(ServerState.LOOKING);
                           }
                       }
                       break;
                   case OBSERVING:
                       try {
                           LOG.info("OBSERVING");
                           setObserver(makeObserver(logFactory));
                           observer.observeLeader();
                       } catch (Exception e) {
                           LOG.warn("Unexpected exception",e );                        
                       } finally {
                           observer.shutdown();
                           setObserver(null);
                           setPeerState(ServerState.LOOKING);
                       }
                       break;
                   case FOLLOWING:
                       try {
                           LOG.info("FOLLOWING");
                           setFollower(makeFollower(logFactory));
                           follower.followLeader();
                       } catch (Exception e) {
                           LOG.warn("Unexpected exception",e);
                       } finally {
                           follower.shutdown();
                           setFollower(null);
                           setPeerState(ServerState.LOOKING);
                       }
                       break;
                   case LEADING:
                       LOG.info("LEADING");
                       try {
                           setLeader(makeLeader(logFactory));
                           leader.lead();
                           setLeader(null);
                       } catch (Exception e) {
                           LOG.warn("Unexpected exception",e);
                       } finally {
                           if (leader != null) {
                               leader.shutdown("Forcing shutdown");
                               setLeader(null);
                           }
                           setPeerState(ServerState.LOOKING);
                       }
                       break;
                   }
               }
           } finally {
               LOG.warn("QuorumPeer main thread exited");
               try {
                   MBeanRegistry.getInstance().unregisterAll();
               } catch (Exception e) {
                   LOG.warn("Failed to unregister with JMX", e);
               }
               jmxQuorumBean = null;
               jmxLocalPeerBean = null;
           }
       } 
        
   }
   ```

   ## Leader 选举

   ```java
    //  QuorumPeer 本质是一个线程
   public class QuorumPeer extends ZooKeeperThread implements QuorumStats.Provider {
       
       // leader 选举
    	synchronized public void startLeaderElection() {
       	try {
       		currentVote = new Vote(myid, getLastLoggedZxid(), getCurrentEpoch()); // 1.当前的投票信息
       	} catch(IOException e) {
       		RuntimeException re = new RuntimeException(e.getMessage());
       		re.setStackTrace(e.getStackTrace());
       		throw re;
       	}
           for (QuorumServer p : getView().values()) {
               if (p.id == myid) {
                   myQuorumAddr = p.addr;
                   break;
               }
           }
           if (myQuorumAddr == null) {
               throw new RuntimeException("My id " + myid + " not in the peer list");
           }
           if (electionType == 0) { // 3.4.6版本后不再有
               try {
                   udpSocket = new DatagramSocket(myQuorumAddr.getPort());
                   responder = new ResponderThread();
                   responder.start();
               } catch (SocketException e) {
                   throw new RuntimeException(e);
               }
           }
           this.electionAlg = createElectionAlgorithm(electionType); // 2.创建选举的算法
       }
       
   }
   ```

   `org.apache.zookeeper.server.quorum.QuorumCnxManager`

   ```java
   // 管理当前节点向其他节点信息的接收发送
   public class QuorumCnxManager {
   // visible for testing
       public QuorumCnxManager(final long mySid,
                               Map<Long,QuorumPeer.QuorumServer> view,
                               QuorumAuthServer authServer,
                               QuorumAuthLearner authLearner,
                               int socketTimeout,
                               boolean listenOnAllIPs,
                               int quorumCnxnThreadsSize,
                               boolean quorumSaslAuthEnabled,
                               ConcurrentHashMap<Long, SendWorker> senderWorkerMap) {
           this.senderWorkerMap = senderWorkerMap;
   
           this.recvQueue = new ArrayBlockingQueue<Message>(RECV_CAPACITY); // 接收队列
           this.queueSendMap = new ConcurrentHashMap<Long, ArrayBlockingQueue<ByteBuffer>>(); // 向其他节点的发送队列
           this.lastMessageSent = new ConcurrentHashMap<Long, ByteBuffer>();
           String cnxToValue = System.getProperty("zookeeper.cnxTimeout");
           if(cnxToValue != null){
               this.cnxTO = Integer.parseInt(cnxToValue);
           }
   
           this.mySid = mySid;
           this.socketTimeout = socketTimeout;
           this.view = view;
           this.listenOnAllIPs = listenOnAllIPs;
   
           initializeAuth(mySid, authServer, authLearner, quorumCnxnThreadsSize,
                   quorumSaslAuthEnabled);
   
           // Starts listener thread that waits for connection requests 
           listener = new Listener();
       }
   }
   ```

   

   `org.apache.zookeeper.server.quorum.FastLeaderElection`

   ```java
   // 快速选举
   public class FastLeaderElection implements Election {
   
   	public FastLeaderElection(QuorumPeer self, QuorumCnxManager manager){
           this.stop = false;
           this.manager = manager;
           starter(self, manager);
       }
       
       LinkedBlockingQueue<ToSend> sendqueue;
       LinkedBlockingQueue<Notification> recvqueue;
       
       private void starter(QuorumPeer self, QuorumCnxManager manager) {
           this.self = self;
           proposedLeader = -1;
           proposedZxid = -1;
   
           sendqueue = new LinkedBlockingQueue<ToSend>(); // 发送队列 
           recvqueue = new LinkedBlockingQueue<Notification>(); // 接收队列
           this.messenger = new Messenger(manager); // 创建接收线程和发送线程，通过队列实现异步化处理请求
       }
       // ... ...
       // 消息处理
       protected class Messenger {
   
           /**
            * Receives messages from instance of QuorumCnxManager on
            * method run(), and processes such messages.
            */
   
           class WorkerReceiver extends ZooKeeperThread {
               volatile boolean stop;
               QuorumCnxManager manager;
   
               WorkerReceiver(QuorumCnxManager manager) {
                   super("WorkerReceiver");
                   this.stop = false;
                   this.manager = manager;
               }
   
               public void run() {
   
                   Message response;
                   while (!stop) {
                       // Sleeps on receive
                       try{
                           response = manager.pollRecvQueue(3000, TimeUnit.MILLISECONDS);
                           if(response == null) continue;
   
                           /*
                            * If it is from an observer, respond right away.
                            * Note that the following predicate assumes that
                            * if a server is not a follower, then it must be
                            * an observer. If we ever have any other type of
                            * learner in the future, we'll have to change the
                            * way we check for observers.
                            */
                           if(!self.getVotingView().containsKey(response.sid)){
                               Vote current = self.getCurrentVote();
                               ToSend notmsg = new ToSend(ToSend.mType.notification,
                                       current.getId(),
                                       current.getZxid(),
                                       logicalclock.get(),
                                       self.getPeerState(),
                                       response.sid,
                                       current.getPeerEpoch());
   
                               sendqueue.offer(notmsg);
                           } else {
                               // Receive new message
                               if (LOG.isDebugEnabled()) {
                                   LOG.debug("Receive new notification message. My id = "
                                           + self.getId());
                               }
   
                               /*
                                * We check for 28 bytes for backward compatibility
                                */
                               if (response.buffer.capacity() < 28) {
                                   LOG.error("Got a short response: "
                                           + response.buffer.capacity());
                                   continue;
                               }
                               boolean backCompatibility = (response.buffer.capacity() == 28);
                               response.buffer.clear();
   
                               // Instantiate Notification and set its attributes
                               Notification n = new Notification();
                               
                               // State of peer that sent this message
                               QuorumPeer.ServerState ackstate = QuorumPeer.ServerState.LOOKING;
                               switch (response.buffer.getInt()) {
                               case 0:
                                   ackstate = QuorumPeer.ServerState.LOOKING;
                                   break;
                               case 1:
                                   ackstate = QuorumPeer.ServerState.FOLLOWING;
                                   break;
                               case 2:
                                   ackstate = QuorumPeer.ServerState.LEADING;
                                   break;
                               case 3:
                                   ackstate = QuorumPeer.ServerState.OBSERVING;
                                   break;
                               default:
                                   continue;
                               }
                               
                               n.leader = response.buffer.getLong();
                               n.zxid = response.buffer.getLong();
                               n.electionEpoch = response.buffer.getLong();
                               n.state = ackstate;
                               n.sid = response.sid;
                               if(!backCompatibility){
                                   n.peerEpoch = response.buffer.getLong();
                               } else {
                                   if(LOG.isInfoEnabled()){
                                       LOG.info("Backward compatibility mode, server id=" + n.sid);
                                   }
                                   n.peerEpoch = ZxidUtils.getEpochFromZxid(n.zxid);
                               }
   
                               /*
                                * Version added in 3.4.6
                                */
   
                               n.version = (response.buffer.remaining() >= 4) ? 
                                            response.buffer.getInt() : 0x0;
   
                               /*
                                * Print notification info
                                */
                               if(LOG.isInfoEnabled()){
                                   printNotification(n);
                               }
   
                               /*
                                * If this server is looking, then send proposed leader
                                */
   
                               if(self.getPeerState() == QuorumPeer.ServerState.LOOKING){
                                   recvqueue.offer(n);
   
                                   /*
                                    * Send a notification back if the peer that sent this
                                    * message is also looking and its logical clock is
                                    * lagging behind.
                                    */
                                   if((ackstate == QuorumPeer.ServerState.LOOKING)
                                           && (n.electionEpoch < logicalclock.get())){
                                       Vote v = getVote();
                                       ToSend notmsg = new ToSend(ToSend.mType.notification,
                                               v.getId(),
                                               v.getZxid(),
                                               logicalclock.get(),
                                               self.getPeerState(),
                                               response.sid,
                                               v.getPeerEpoch());
                                       sendqueue.offer(notmsg);
                                   }
                               } else {
                                   /*
                                    * If this server is not looking, but the one that sent the ack
                                    * is looking, then send back what it believes to be the leader.
                                    */
                                   Vote current = self.getCurrentVote();
                                   if(ackstate == QuorumPeer.ServerState.LOOKING){
                                       if(LOG.isDebugEnabled()){
                                           LOG.debug("Sending new notification. My id =  " +
                                                   self.getId() + " recipient=" +
                                                   response.sid + " zxid=0x" +
                                                   Long.toHexString(current.getZxid()) +
                                                   " leader=" + current.getId());
                                       }
                                       
                                       ToSend notmsg;
                                       if(n.version > 0x0) {
                                           notmsg = new ToSend(
                                                   ToSend.mType.notification,
                                                   current.getId(),
                                                   current.getZxid(),
                                                   current.getElectionEpoch(),
                                                   self.getPeerState(),
                                                   response.sid,
                                                   current.getPeerEpoch());
                                           
                                       } else {
                                           Vote bcVote = self.getBCVote();
                                           notmsg = new ToSend(
                                                   ToSend.mType.notification,
                                                   bcVote.getId(),
                                                   bcVote.getZxid(),
                                                   bcVote.getElectionEpoch(),
                                                   self.getPeerState(),
                                                   response.sid,
                                                   bcVote.getPeerEpoch());
                                       }
                                       sendqueue.offer(notmsg);
                                   }
                               }
                           }
                       } catch (InterruptedException e) {
                           System.out.println("Interrupted Exception while waiting for new message" +
                                   e.toString());
                       }
                   }
                   LOG.info("WorkerReceiver is down");
               }
           }
   
   
           /**
            * This worker simply dequeues a message to send and
            * and queues it on the manager's queue.
            */
   
           class WorkerSender extends ZooKeeperThread {
               volatile boolean stop;
               QuorumCnxManager manager;
   
               WorkerSender(QuorumCnxManager manager){
                   super("WorkerSender");
                   this.stop = false;
                   this.manager = manager;
               }
   
               public void run() {
                   while (!stop) {
                       try {
                           ToSend m = sendqueue.poll(3000, TimeUnit.MILLISECONDS);
                           if(m == null) continue;
   
                           process(m);
                       } catch (InterruptedException e) {
                           break;
                       }
                   }
                   LOG.info("WorkerSender is down");
               }
   
               /**
                * Called by run() once there is a new message to send.
                *
                * @param m     message to send
                */
               void process(ToSend m) {
                   ByteBuffer requestBuffer = buildMsg(m.state.ordinal(), 
                                                           m.leader,
                                                           m.zxid, 
                                                           m.electionEpoch, 
                                                           m.peerEpoch);
                   manager.toSend(m.sid, requestBuffer); // 这里通过sid找到对应的发送队列添加进去，若没有则先创建队列和连接，
               }
           }
   
   
           WorkerSender ws;
           WorkerReceiver wr;
   
           /**
            * Constructor of class Messenger.
            *
            * @param manager   Connection manager
            */
           Messenger(QuorumCnxManager manager) {
   
               this.ws = new WorkerSender(manager);
   
               Thread t = new Thread(this.ws,
                       "WorkerSender[myid=" + self.getId() + "]");
               t.setDaemon(true);
               t.start();
   
               this.wr = new WorkerReceiver(manager);
   
               t = new Thread(this.wr,
                       "WorkerReceiver[myid=" + self.getId() + "]");
               t.setDaemon(true);
               t.start();
           }
   
           /**
            * Stops instances of WorkerSender and WorkerReceiver
            */
           void halt(){
               this.ws.stop = true;
               this.wr.stop = true;
           }
   
       }
       
   }
   
   ```

   

# 应用

Leader 选举，分布式锁，注册中心，配置中心

## 基于Curator的leader选举

`com.gupao.study.zookeeper.curator.leaderselector.LeaderSelectorDemo`

## 基于Curator 的分布式锁实现

1. 自定义实现`com.gupao.study.zookeeper.distribute.lock.DistributeLockTestDemo` 
2. Curator 自带的分布式锁实现`com.gupao.study.zookeeper.curator.lock.CuratorLockDemo`

原理：

1. Zookeeper节点监听机制，监听节点的删除修改事件

2. Zookeeper 临时有序节点的使用，通过比较目录节点下的 当前需要获取锁的线程创建的节点是否为最小值，如果为最小值则获取锁成功，执行完成业务程序后删除节点释放锁。

