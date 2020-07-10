package com.study.gupao.netty.protobuf;

import com.study.gupao.netty.NettyClient;
import com.study.gupao.netty.protobuf.core.ProtoBufSocketChannelInitializer;

public class ProtoBufNettyClient extends NettyClient {

    public ProtoBufNettyClient(int port) {
        super(port, new ProtoBufSocketChannelInitializer());
    }
}
