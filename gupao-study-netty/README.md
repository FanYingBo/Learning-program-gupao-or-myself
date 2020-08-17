# 异步非阻塞

NioEventLoop 聚合了多路复用器Selector，由于读写操作都是非阻塞的，充分提升了IO线程的运行效率，避免由于频繁的IO阻塞导致的线程挂起产生的对CPU资源的浪费

# 零拷贝

* Java 中的零拷贝 ByteBuffer.allocateDirect(nums)，直接在堆外申请内存（Ring3->Ring0  用户态切换到内核态），通过C的malloc来进行分配的，分配的内存是系统本地的内存，申请后会保留内存地址（`long address`）。但是堆外内存的申请开销极大，而且使用DirectByteBuffer，内存空间不够的情况下，会触发System.gc() （Full GC）回收。
* Netty中默认使用了Java的DirectByteBuffer，使用了内存的池化技术（PooledUnsafeDirectByteBuf ，PooledUnsafeHeapByteBuf），这些在Netty都是对内存使用的划分（Pooled 和UnPooled ，Safe和Unsafe，Heap和Direct）

# 线程模型

* Reactor 单线程模型 
* Reactor 多线程模型
* 主从Reactor多线程模型

# 无锁化串行设计

* Channel.pipeLine() 串行化处理InBound (decode)和OutBound(encode)事件，完成数据的编解码，并最终到达消息处理器

# Netty 高性能之道

1. volatile 的大量使用，正确使用
2. CAS和原子类的使用
3. 线程安全容器的使用
4. 通过读写锁提高并发性能

# 编解码（序列化）的性能影响

1. 序列化后的字节大小（网络带宽）
2. 序列化与反序列化的性能（占用CPU运算的时间）
3. 是否支持跨语言环境（不同语言之间的开发和对接）

# 灵活的TCP参数配置

```java

    public static final ChannelOption<WriteBufferWaterMark> WRITE_BUFFER_WATER_MARK =
            valueOf("WRITE_BUFFER_WATER_MARK"); // channel.isWritable() 通过高水平线与低水平线判断是否可写

    public static final ChannelOption<Boolean> ALLOW_HALF_CLOSURE = valueOf("ALLOW_HALF_CLOSURE");
    public static final ChannelOption<Boolean> AUTO_READ = valueOf("AUTO_READ");

    /**
     * If {@code true} then the {@link Channel} is closed automatically and immediately on write failure.
     * The default value is {@code true}.
     */
    public static final ChannelOption<Boolean> AUTO_CLOSE = valueOf("AUTO_CLOSE");

    public static final ChannelOption<Boolean> SO_BROADCAST = valueOf("SO_BROADCAST");
    public static final ChannelOption<Boolean> SO_KEEPALIVE = valueOf("SO_KEEPALIVE");
    public static final ChannelOption<Integer> SO_SNDBUF = valueOf("SO_SNDBUF");
    public static final ChannelOption<Integer> SO_RCVBUF = valueOf("SO_RCVBUF");
    public static final ChannelOption<Boolean> SO_REUSEADDR = valueOf("SO_REUSEADDR");
    public static final ChannelOption<Integer> SO_LINGER = valueOf("SO_LINGER");
    public static final ChannelOption<Integer> SO_BACKLOG = valueOf("SO_BACKLOG");
    public static final ChannelOption<Integer> SO_TIMEOUT = valueOf("SO_TIMEOUT");

    public static final ChannelOption<Integer> IP_TOS = valueOf("IP_TOS");
    public static final ChannelOption<InetAddress> IP_MULTICAST_ADDR = valueOf("IP_MULTICAST_ADDR");
    public static final ChannelOption<NetworkInterface> IP_MULTICAST_IF = valueOf("IP_MULTICAST_IF");
    public static final ChannelOption<Integer> IP_MULTICAST_TTL = valueOf("IP_MULTICAST_TTL");
    public static final ChannelOption<Boolean> IP_MULTICAST_LOOP_DISABLED = valueOf("IP_MULTICAST_LOOP_DISABLED");

    public static final ChannelOption<Boolean> TCP_NODELAY = valueOf("TCP_NODELAY");
```

## 参数分类

Netty 的参数分类通过不同的ChannelConfig来管理，

1. DefaultServerSocketChannelConfig  服务端ServerSocketChnanel参数管理:

   ```
   SO_RCVBUF
     Socket参数，TCP数据接收缓冲区大小。该缓冲区即TCP接收滑动窗口，linux操作系统可使用命令：cat /proc/sys/net/ipv4/tcp_rmem 查询其大小。一般情况下，该值可由用户在任意时刻设置，但当设置值超过64KB时，需要在连接到远端之前设置。需要注意的是：当设置值超过64KB时，需要在绑定到本地端口前设置。该值设置的是由ServerSocketChannel使用accept接受的SocketChannel的接收缓冲区。
   SO_REUSEADDR
     Socket参数，地址复用，默认值False。有四种情况可以使用：(1).当有一个有相同本地地址和端口的socket1处于TIME_WAIT状态时，而你希望启动的程序的socket2要占用该地址和端口，比如重启服务且保持先前端口。(2).有多块网卡或用IP Alias技术的机器在同一端口启动多个进程，但每个进程绑定的本地IP地址不能相同。(3).单个进程绑定相同的端口到多个socket上，但每个socket绑定的ip地址不同。(4).完全相同的地址和端口的重复绑定。但这只用于UDP的多播，不用于TCP。
   SO_BACKLOG
     Socket参数，服务端接受连接的队列长度，如果队列已满，客户端连接将被拒绝。默认值，Windows为200，其他为128。
   ```

   ```java
   public class DefaultServerSocketChannelConfig extends DefaultChannelConfig
                                                 implements ServerSocketChannelConfig {
   	// 这里在设置参数时候会验证是否是可以设置
       @Override
       public <T> boolean setOption(ChannelOption<T> option, T value) {
           validate(option, value);
   
           if (option == SO_RCVBUF) { // 
               setReceiveBufferSize((Integer) value);
           } else if (option == SO_REUSEADDR) {
               setReuseAddress((Boolean) value);
           } else if (option == SO_BACKLOG) {
               setBacklog((Integer) value);
           } else {
               return super.setOption(option, value);
           }
   
           return true;
       }
       // ... ...
   }
   ```

2. DefaultSocketChannelConfig  服务端 通过`io.netty.bootstrap.ServerBootstrap.ServerBootstrapAcceptor`接收连接后对SocketChannel参数管理

   ```
   SO_RCVBUF
     Socket参数，TCP数据接收缓冲区大小。该缓冲区即TCP接收滑动窗口，linux操作系统可使用命令：cat /proc/sys/net/ipv4/tcp_rmem查询其大小。一般情况下，该值可由用户在任意时刻设置，但当设置值超过64KB时，需要在连接到远端之前设置。
   SO_SNDBUF
     Socket参数，TCP数据发送缓冲区大小。该缓冲区即TCP发送滑动窗口，linux操作系统可使用命令：cat /proc/sys/net/ipv4/tcp_smem查询其大小。
   TCP_NODELAY
     TCP参数，立即发送数据，默认值为Ture（Netty默认为True而操作系统默认为False）。该值设置Nagle算法的启用，改算法将小的碎片数据连接成更大的报文来最小化所发送的报文的数量，如果需要发送一些较小的报文，则需要禁用该算法。Netty默认禁用该算法，从而最小化报文传输延时。
   SO_KEEPALIVE
     Socket参数，连接保活，默认值为False。启用该功能时，TCP会主动探测空闲连接的有效性。可以将此功能视为TCP的心跳机制，需要注意的是：默认的心跳间隔是7200s即2小时。Netty默认关闭该功能。
   SO_REUSEADDR
     Socket参数，地址复用，默认值False。有四种情况可以使用：(1).当有一个有相同本地地址和端口的socket1处于TIME_WAIT状态时，而你希望启动的程序的socket2要占用该地址和端口，比如重启服务且保持先前端口。(2).有多块网卡或用IP Alias技术的机器在同一端口启动多个进程，但每个进程绑定的本地IP地址不能相同。(3).单个进程绑定相同的端口到多个socket上，但每个socket绑定的ip地址不同。(4).完全相同的地址和端口的重复绑定。但这只用于UDP的多播，不用于TCP。
   SO_LINGER
     Socket参数，关闭Socket的延迟时间，默认值为-1，表示禁用该功能。-1表示socket.close()方法立即返回，但OS底层会将发送缓冲区全部发送到对端。0表示socket.close()方法立即返回，OS放弃发送缓冲区的数据直接向对端发送RST包，对端收到复位错误。非0整数值表示调用socket.close()方法的线程被阻塞直到延迟时间到或发送缓冲区中的数据发送完毕，若超时，则对端会收到复位错误。
   IP_TOS
     IP参数，设置IP头部的Type-of-Service字段，用于描述IP包的优先级和QoS选项。
   ALLOW_HALF_CLOSURE
     Netty参数，一个连接的远端关闭时本地端是否关闭，默认值为False。值为False时，连接自动关闭；为True时，触发ChannelInboundHandler的userEventTriggered()方法，事件为ChannelInputShutdownEvent。
   ```

   ```java
   public class DefaultSocketChannelConfig extends DefaultChannelConfig
                                           implements SocketChannelConfig {
       
       @Override
       public <T> boolean setOption(ChannelOption<T> option, T value) {
           validate(option, value);
   
           if (option == SO_RCVBUF) {
               setReceiveBufferSize((Integer) value);
           } else if (option == SO_SNDBUF) {
               setSendBufferSize((Integer) value);
           } else if (option == TCP_NODELAY) {
               setTcpNoDelay((Boolean) value);
           } else if (option == SO_KEEPALIVE) {
               setKeepAlive((Boolean) value);
           } else if (option == SO_REUSEADDR) {
               setReuseAddress((Boolean) value);
           } else if (option == SO_LINGER) {
               setSoLinger((Integer) value);
           } else if (option == IP_TOS) {
               setTrafficClass((Integer) value);
           } else if (option == ALLOW_HALF_CLOSURE) {
               setAllowHalfClosure((Boolean) value);
           } else {
               return super.setOption(option, value);
           }
   
           return true;
       }
   }
   ```

   

3. DefaultOioServerSocketChannelConfig

   同DefaultServerSocketChannelConfig  

4. DefaultOioSocketChannelConfig

   同DefaultSocketChannelConfig

5. DefaultChannelConfig 通用配置

   ```
   CONNECT_TIMEOUT_MILLIS :
     Netty参数，连接超时毫秒数，默认值30000毫秒即30秒。
   MAX_MESSAGES_PER_READ
     Netty参数，一次Loop读取的最大消息数，对于ServerChannel或者NioByteChannel，默认值为16，其他Channel默认值为1。默认值这样设置，是因为：ServerChannel需要接受足够多的连接，保证大吞吐量，NioByteChannel可以减少不必要的系统调用select。
   WRITE_SPIN_COUNT
     Netty参数，一个Loop写操作执行的最大次数，默认值为16。也就是说，对于大数据量的写操作至多进行16次，如果16次仍没有全部写完数据，此时会提交一个新的写任务给EventLoop，任务将在下次调度继续执行。这样，其他的写请求才能被响应不会因为单个大数据量写请求而耽误。
   ALLOCATOR
     Netty参数，ByteBuf的分配器，默认值为ByteBufAllocator.DEFAULT，4.0版本为UnpooledByteBufAllocator，4.1版本为PooledByteBufAllocator。该值也可以使用系统参数io.netty.allocator.type配置，使用字符串值："unpooled"，"pooled"。
   RCVBUF_ALLOCATOR
     Netty参数，用于Channel分配接受Buffer的分配器，默认值为AdaptiveRecvByteBufAllocator.DEFAULT，是一个自适应的接受缓冲区分配器，能根据接受到的数据自动调节大小。可选值为FixedRecvByteBufAllocator，固定大小的接受缓冲区分配器。
   AUTO_READ
     Netty参数，自动读取，默认值为True。Netty只在必要的时候才设置关心相应的I/O事件。对于读操作，需要调用channel.read()设置关心的I/O事件为OP_READ，这样若有数据到达才能读取以供用户处理。该值为True时，每次读操作完毕后会自动调用channel.read()，从而有数据到达便能读取；否则，需要用户手动调用channel.read()。需要注意的是：当调用config.setAutoRead(boolean)方法时，如果状态由false变为true，将会调用channel.read()方法读取数据；由true变为false，将调用config.autoReadCleared()方法终止数据读取。
   WRITE_BUFFER_HIGH_WATER_MARK
     Netty参数，写高水位标记，默认值64KB。如果Netty的写缓冲区中的字节超过该值，Channel的isWritable()返回False。
   WRITE_BUFFER_LOW_WATER_MARK
     Netty参数，写低水位标记，默认值32KB。当Netty的写缓冲区中的字节超过高水位之后若下降到低水位，则Channel的isWritable()返回True。写高低水位标记使用户可以控制写入数据速度，从而实现流量控制。推荐做法是：每次调用channl.write(msg)方法首先调用channel.isWritable()判断是否可写。
   MESSAGE_SIZE_ESTIMATOR
     Netty参数，消息大小估算器，默认为DefaultMessageSizeEstimator.DEFAULT。估算ByteBuf、ByteBufHolder和FileRegion的大小，其中ByteBuf和ByteBufHolder为实际大小，FileRegion估算值为0。该值估算的字节数在计算水位时使用，FileRegion为0可知FileRegion不影响高低水位。
   SINGLE_EVENTEXECUTOR_PER_GROUP
     Netty参数，单线程执行ChannelPipeline中的事件，默认值为True。该值控制执行ChannelPipeline中执行ChannelHandler的线程。如果为Trye，整个pipeline由一个线程执行，这样不需要进行线程切换以及线程同步，是Netty4的推荐做法；如果为False，ChannelHandler中的处理过程会由Group中的不同线程执行。
   ```

   ```java
    public class DefaultChannelConfig implements ChannelConfig {
    	@Override
       @SuppressWarnings("deprecation")
       public <T> boolean setOption(ChannelOption<T> option, T value) {
           validate(option, value);
   
           if (option == CONNECT_TIMEOUT_MILLIS) {
               setConnectTimeoutMillis((Integer) value);
           } else if (option == MAX_MESSAGES_PER_READ) {
               setMaxMessagesPerRead((Integer) value);
           } else if (option == WRITE_SPIN_COUNT) {
               setWriteSpinCount((Integer) value);
           } else if (option == ALLOCATOR) {
               setAllocator((ByteBufAllocator) value);
           } else if (option == RCVBUF_ALLOCATOR) {
               setRecvByteBufAllocator((RecvByteBufAllocator) value);
           } else if (option == AUTO_READ) {
               setAutoRead((Boolean) value);
           } else if (option == AUTO_CLOSE) {
               setAutoClose((Boolean) value);
           } else if (option == WRITE_BUFFER_HIGH_WATER_MARK) {
               setWriteBufferHighWaterMark((Integer) value);
           } else if (option == WRITE_BUFFER_LOW_WATER_MARK) {
               setWriteBufferLowWaterMark((Integer) value);
           } else if (option == WRITE_BUFFER_WATER_MARK) {
               setWriteBufferWaterMark((WriteBufferWaterMark) value);
           } else if (option == MESSAGE_SIZE_ESTIMATOR) {
               setMessageSizeEstimator((MessageSizeEstimator) value);
           } else if (option == SINGLE_EVENTEXECUTOR_PER_GROUP) {
               setPinEventExecutorPerGroup((Boolean) value);
           } else {
               return false;
           }
   
           return true;
       }
   }
   ```

   

   

    



# 部分参数解析

* SO_BACKLOG

  backlog其实是一个连接队列，在Linux内核2.2之前，backlog大小包括半连接状态（SYN QUEUE）和全连接状态（ACCPET QUEUE）两种队列大小。

  1. 三次握手中，在第一步server收到client的syn后，把这个连接信息放到半连接队列中，同时回复syn+ack给client，第二步客户端发送ACK给服务端，建立连接，服务端在收到ACK后，将这个连接放入ACCEPCT QUEUE中，如果全连接队列满了就会按照`tcp_abort_on_overflow`的参数设置执行，如果`tcp_abort_on_overflow`为零的话就会按照`tcp_synack_retries`参数进行重试，如果客户端超时等待时间比较短客户端就会出现异常，无法建立连接。

  2. 半连接大小由 端口号被listen时设置的backlog  通过netstat -l 命令查看时 Recv-Q 的队列数值（SYN等待的个数）客户端通过 connect() 去连接正在 listen() 的服务端时，这些连接会一直处于这个 queue 里面直到被服务端 accept()；

  3. 全连接大小由  min(backlog,somaxconn)  (/proc/sys/net/core/somaxconn 的值)决定 全连接是netstat -l 命令查看时 Send-Q 的队列数值

  4. 对应的Linux服务器相关参数

  |                 参数名                  | 意义                                                         | 取值                 |
  | :-------------------------------------: | ------------------------------------------------------------ | -------------------- |
  |  proc/sys/net/ipv4/tcp_max_syn_backlog  | 服务器端在接受客户端的SYN请求之后，在记忆领域可存储的客户端SYN请求数 | 1024(不同系统不一样) |
  |  proc/sys/net/ipv4/tcp_synack_retries   | 服务器端处于SYN_RECV状态之后，在一定时间没有收到客户端的SYN时，再次发送SYN ACK的次数 | 5(默认)              |
  | proc/sys/net/ipv4/tcp_abort_on_overflow | 当服务器端高负荷时，给客户端发送RST（Connect reset）切断连接来保护服务器 | 0(默认无效)          |
  |      /proc/sys/net/core/somaxconn       | ACCEPT QUEUE 也称为全连接队列的大小，由该值min(backlog,somaxconn)决定 | 128(不同系统不一样)  |
  |    /proc/sys/net/ipv4/tcp_syncookies    | SYN cookies是为了防止TCP SYN flood攻击开发的对应方法之一     | 0是无效，1是有效。   |

  ```
  经典题：
  * TCP建立连接是要进行三次握手，但是否完成三次握手后，服务器就处理（accept）呢
  ```

* TCP_NODELAY

  Socket编程中，TCP_NODELAY选项是用来控制是否开启Nagle算法，该算法是为了提高较慢的广域网传输效率，减小小分组的报文个数，该算法要求一个TCP连接上最多只能有一个未被确认的小分组，在该小分组的确认到来之前，不能发送其他小分组。这里的小分组指的是报文长度小于MSS(Max Segment Size)长度的分组（MSS是在TCP握手的时候在报文选项里面进行通告的大小，主要是用来限制另一端发送数据的长度，防止IP数据包被分段，提高效率，一般是链路层的传输最大传输单元大小减去IP首部与TCP首部大小）；

  Nagle算法的规则（可参考tcp_output.c文件里tcp_nagle_check函数注释）：

  （1）如果包（tcp报文-tcp报头）长度达到MSS，则允许发送；

  （2）如果该包含有FIN，则允许发送；

  （3）设置了TCP_NODELAY选项，则允许发送；

  （4）未设置TCP_CORK选项时，若所有发出去的小数据包（包长度小于MSS）均被确认，则允许发送；

  （5）上述条件都未满足，但发生了超时（一般为200ms），则立即发送。

  ```
  注：
   读缓冲区（滑动窗口）耗尽与write阻塞、拆包、延迟中的情况不属于nagle范畴的允许发送。
   原因一：当缓冲区大小与包大小相等，在等待ack的过程中，发送端只能放一个包，如果关闭TCP_NODELAY（开启nagle算法），就会等待第一个包获得ACK相应后才会唤醒write，这是write不能立即发送和阻塞的原因；
   原因二：在未启用nagle算法的时候，虽然每次write不需要等待ACK响应，能够继续发送，但由于滑动窗口的大小（缓冲区）限制，客户端会返回给你窗口大小（客户端最多能接受多少字节的数据）
  ```

  

* SO_KEEPALIVE

  TCP内嵌有心跳包,以服务端为例,当server检测到超过一定时间(/proc/sys/net/ipv4/tcp_keepalive_time 7200 即2小时)没有数据传输,那么会向client端发送一个keepalive packet,此时client端有三种反应:

  1. client端连接正常,返回一个ACK.server端收到ACK后重置计时器,在2小时后在发送探测.如果2小时内连接上有数据传输,那么在该时间的基础上向后推延2小时发送探测包;
  2. 客户端异常关闭,或网络断开。client无响应,server收不到ACK,在一定时间(/proc/sys/net/ipv4/tcp_keepalive_intvl 75 即75秒)后重发keepalive packet, 并且重发一定次数(/proc/sys/net/ipv4/tcp_keepalive_probes 9 即9次);
  3. 客户端曾经崩溃,但已经重启.server收到的探测响应是一个复位,server端终止连接。

  | 参数名                                  | 意义                                        | 取值         |
  | --------------------------------------- | ------------------------------------------- | ------------ |
  | /proc/sys/net/ipv4/tcp_keepalive_time   | 发送心跳（ACK）的周期时间                   | 7200（默认） |
  | /proc/sys/net/ipv4/tcp_keepalive_probes | 没有收到心跳时继续发送ACK的次数             | 9（默认）    |
  | /proc/sys/net/ipv4/tcp_keepalive_intvl  | 没有收到心跳时继续发送ACK的频率（时间间隔） | 75           |

  

  Netty 相关问题 https://blog.csdn.net/jiao1902676909/article/details/90647497

  

  
  
  