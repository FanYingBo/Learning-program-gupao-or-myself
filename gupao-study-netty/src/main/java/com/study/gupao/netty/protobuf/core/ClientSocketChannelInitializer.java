package com.study.gupao.netty.protobuf.core;

import com.study.gupao.netty.protobuf.res.FirstResponse;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

public class ClientSocketChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline()
                // 对接收到的数据进行切割
                .addLast(new ProtobufVarint32FrameDecoder())
                // 这里是对二进制数据解码成具体的实例
                .addLast(new ProtobufDecoder(FirstResponse.FirstRes.getDefaultInstance()))
                .addLast(new ProtobufVarint32LengthFieldPrepender())
                .addLast(new ProtobufEncoder())
                .addLast(new SimpleClientChannelInboundHandler());
    }
}
