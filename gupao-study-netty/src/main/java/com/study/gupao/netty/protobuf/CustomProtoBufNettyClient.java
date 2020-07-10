package com.study.gupao.netty.protobuf;

import com.study.gupao.netty.NettyClient;
import com.study.gupao.netty.protobuf.core.custom.CustomClientProtoBufChannelInitializer;

public class CustomProtoBufNettyClient extends NettyClient {

    public CustomProtoBufNettyClient(int port) {
        super(port, new CustomClientProtoBufChannelInitializer());
    }
}
