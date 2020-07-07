package com.study.gupao.netty.http;

import com.study.gupao.netty.NettyServer;
import io.netty.channel.ChannelInboundHandler;

public class HttpNettyServer extends NettyServer {

    public HttpNettyServer(int port) {
        super(port);
    }

    @Override
    public ChannelInboundHandler handler() {
        return new HttpServerInitializer();
    }
}
