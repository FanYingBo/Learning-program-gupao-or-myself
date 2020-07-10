package com.study.gupao.netty.protobuf;

import com.study.gupao.netty.NettyServer;
import com.study.gupao.netty.protobuf.core.custom.CustomProtoBufChannelInitializer;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class CustomProtoBufNettyServer extends NettyServer {

    private ChannelInitializer<SocketChannel> channelInitializer;

    public CustomProtoBufNettyServer(int port) {
        super(port);
        channelInitializer = new CustomProtoBufChannelInitializer();
    }

    @Override
    public ChannelInboundHandler handler() {
        return channelInitializer;
    }
}
