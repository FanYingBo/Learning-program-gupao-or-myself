package com.study.gupao.netty;

import com.study.gupao.io.AbstractIOServer;
import com.study.gupao.netty.core.SocketChannelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class NettyServer extends AbstractIOServer {
    private Log log = LogFactory.getLog(NettyServer.class);

    private int port;
    private ServerBootstrap serverBootstrap;
    private EventLoopGroup bossGroup;
    private EventLoopGroup workGroup;
    private ChannelFuture channelFuture;

    public NettyServer(int port){
        this.port = port;
    }

    private void init(){
        try {
            this.bossGroup = new NioEventLoopGroup();
            this.workGroup = new NioEventLoopGroup();
            serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup,workGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new SocketChannelInitializer());
            channelFuture = serverBootstrap.bind("192.168.8.102",this.port).sync();
            // channelFuture.channel().close().sync(); 这里会执行finally
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("bind error ",e);
        }finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
    @Override
    public void start() {
        init();
        bind();
    }

    @Override
    public boolean close() {
        return false;
    }

    private void bind(){

    }
}
