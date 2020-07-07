package com.study.gupao.netty.protobuf;

import com.study.gupao.netty.NettyServer;
import com.study.gupao.netty.protobuf.core.ProtoBufSocketChannelInitializer;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class ProtoBufNettyServer extends NettyServer {

    private ChannelInitializer<SocketChannel> handler;

    public ProtoBufNettyServer(int port) {
        super(port);
        this.handler = new ProtoBufSocketChannelInitializer();
    }

    @Override
    public ChannelInboundHandler handler() {
        return this.handler;
    }
}
