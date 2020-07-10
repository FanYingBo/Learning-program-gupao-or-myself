package com.study.gupao.netty;

import com.study.gupao.io.AbstractIOClient;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public abstract class NettyClient  extends AbstractIOClient {

    private Bootstrap bootstrap;
    private NioEventLoopGroup nioEventLoop;
    private int port;
    protected ChannelInitializer<SocketChannel> channelInitializer;

    public NettyClient(int port,ChannelInitializer channelInitializer){
        this.port = port;
        this.channelInitializer = channelInitializer;
    }
    @Override
    public void start() throws Exception {
        bootstrap = new Bootstrap();
        nioEventLoop = new NioEventLoopGroup();
        bootstrap.group(nioEventLoop)
                .channel(NioSocketChannel.class)
                .handler(channelInitializer);
            ChannelFuture channelFuture = bootstrap.connect("192.168.8.102",port).sync();
            channelFuture.channel().closeFuture().sync();
    }
    @Override
    public boolean close() {
        nioEventLoop.shutdownGracefully();
        return false;
    }



}
