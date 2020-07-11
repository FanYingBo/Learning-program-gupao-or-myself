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


public abstract class NettyServer extends AbstractIOServer {
    private Log log = LogFactory.getLog(NettyServer.class);

    private ServerBootstrap serverBootstrap;
    private EventLoopGroup bossGroup;
    private EventLoopGroup workGroup;
    private ChannelFuture channelFuture;

    public NettyServer(int port){
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
                // 操作系统参数/proc/sys/net/core/wmem_max
                .option(ChannelOption.SO_SNDBUF,32 * 1024)
                // 操作系统参数/proc/sys/net/core/rmem_max
                .option(ChannelOption.SO_RCVBUF,32 * 1024)
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
            channelFuture = serverBootstrap.bind("192.168.8.102",this.serverPort).sync();
            log.info("The netty nio server has been created and bind port "+this.serverPort);
            // channelFuture.channel().close().sync(); 这里会执行finally
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("bind error ",e);
        }
    }
}
