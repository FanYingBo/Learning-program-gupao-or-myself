package com.study.gupao.netty.protobuf.core.custom;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

public class CustomProtoBufChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline()
                .addLast("frameDecoder",new ProtobufVarint32FrameDecoder())
                .addLast("decoder", new CustomProtoBufDecoder())
                .addLast("frameEncoder",new ProtobufVarint32LengthFieldPrepender())
                .addLast("encoder",new CustomProtoBufEncoder())
                .addLast(new CustomChannelInboundHandler());
    }
}
