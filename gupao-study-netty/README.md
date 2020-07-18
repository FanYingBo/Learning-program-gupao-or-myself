# 异步非阻塞

NioEventLoop 聚合了多路复用器Selector，由于读写操作都是非阻塞的，充分提升了IO线程的运行效率，避免由于频繁的IO阻塞导致的线程挂起产生的对CPU资源的浪费

# 零拷贝

* java 中的零拷贝 ByteBuffer.allocateDirect(nums)，直接在堆外申请内存（Ring3->Ring0  用户态切换到内核态），通过C的malloc来进行分配的，分配的内存是系统本地的内存，申请后会保留内存地址（`long address`）。但是堆外内存的申请开销极大，而且使用DirectByteBuffer，内存空间不够的情况下，会触发System.gc() （Full GC）回收。
* Netty中

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

