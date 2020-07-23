package com.study.gupao.netty;

import com.study.gupao.io.AbstractIOServer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;


public abstract class NettyServer extends AbstractIOServer {
    private Log log = LogFactory.getLog(NettyServer.class);

    private ServerBootstrap serverBootstrap;
    private EventLoopGroup bossGroup;
    private EventLoopGroup workGroup;
    private ChannelFuture channelFuture;
    private String hostName;

    public NettyServer(int port){
        this(null,port);
        try {
            this.hostName = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public NettyServer(String hostName, int port){
        this.hostName = hostName;
        this.serverPort = port;
    }

    public abstract ChannelInboundHandler handler();

    private void init(){
        // 事件循环的线程池
        // 主线程组 用来处理accept
        this.bossGroup = new NioEventLoopGroup();
        // 工作线程组 用来处理数据
        this.workGroup = new NioEventLoopGroup();// 若不设置工作线程则会使用主线程组来处理传播事件
        serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup,workGroup)
                .option(ChannelOption.SO_BACKLOG,128)
                // 操作系统参数/proc/sys/net/core/ewmem_max
//                .option(ChannelOption.SO_SNDBUF,32 * 1024)
                // 操作系统参数/proc/sys/net/core/rmem_max
//                .option(ChannelOption.SO_RCVBUF,32 * 1024)
                //TCP_NODELAY 启用了 证明禁用了Nagle算法，来减小延时。
                //Nagle算法是为了减少广域网的小分组数目，从而减小网络拥塞的出现
                .option(ChannelOption.TCP_NODELAY,Boolean.TRUE)
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(handler());
    }
    @Override
    public void start() {
        init();
        bind();
    }

    @Override
    public boolean close() {
        bossGroup.shutdownGracefully();
        workGroup.shutdownGracefully();
        return false;
    }

    private void bind(){
        try {
            channelFuture = serverBootstrap.bind(this.hostName,this.serverPort).sync();
            log.info("The netty nio server has been created and bind port "+this.serverPort);
            // channelFuture.channel().close().sync(); 这里会执行finally
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("bind error ",e);
        }
    }
}
