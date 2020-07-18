package com.study.gupao.netty.protobuf.core.custom;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

public class CustomProtoBufDecoder extends ByteToMessageDecoder {

    private static final Log log = LogFactory.getLog(CustomProtoBufDecoder.class);
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int bodyLen = in.readInt();

        int mainCmd = in.readInt();
        int subCmd = in.readInt();
        if(in.readableBytes() < bodyLen){ // 这里判断body的长度是为了防止黏包 缓存的数据
            in.resetReaderIndex();
            return ;
        }
        //开始读取核心protobuf数据
        ByteBuf bodyByteBuf = in.readBytes(bodyLen);
        byte[] array;
        //反序列化数据的起始点
        int offset;
        //可读的数据字节长度
        int readableLen= bodyByteBuf.readableBytes();
        //分为包含数组数据和不包含数组数据两种形式
        if (bodyByteBuf.hasArray()) {
            array = bodyByteBuf.array();
            offset = bodyByteBuf.arrayOffset() + bodyByteBuf.readerIndex();
        } else {
            array = new byte[readableLen];
            bodyByteBuf.getBytes(bodyByteBuf.readerIndex(), array, 0, readableLen);
            offset = 0;
        }
        out.add(new CustomMsgData(mainCmd,subCmd,array,offset,readableLen));
    }
}
