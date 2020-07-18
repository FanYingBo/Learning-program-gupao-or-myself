package com.study.gupao.netty;

import com.study.gupao.io.AbstractIOClient;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetAddress;
import java.net.UnknownHostException;

public abstract class NettyClient  extends AbstractIOClient {

    private Bootstrap bootstrap;
    private NioEventLoopGroup nioEventLoop;
    private int port;
    private int nThreads;
    private String hostName;
    protected ChannelInitializer<SocketChannel> channelInitializer;

    public NettyClient(int port,int nThreads, ChannelInitializer channelInitializer){
        this(null, port,nThreads,channelInitializer);
    }

    public NettyClient(String hostName, int port,int nThreads, ChannelInitializer channelInitializer){
        if(hostName == null){ // 如果不指定地址就连本机
            try {
                this.hostName = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }else{
            this.hostName = hostName;
        }
        this.port = port;
        this.channelInitializer = channelInitializer;
        this.nThreads = nThreads;
    }
    @Override
    public void start() throws Exception {
        bootstrap = new Bootstrap();
        nioEventLoop = new NioEventLoopGroup(nThreads);
        bootstrap.group(nioEventLoop)
                .channel(NioSocketChannel.class)
                .handler(channelInitializer);
            ChannelFuture channelFuture = bootstrap.connect(hostName,port).sync();
            channelFuture.channel().closeFuture().sync();
    }
    @Override
    public boolean close() {
        nioEventLoop.shutdownGracefully();
        return false;
    }



}
