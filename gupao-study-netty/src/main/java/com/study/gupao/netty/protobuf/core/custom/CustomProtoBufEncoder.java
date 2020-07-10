package com.study.gupao.netty.protobuf.core.custom;


import com.study.gupao.netty.util.BytesUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class CustomProtoBufEncoder extends MessageToByteEncoder<CustomMsgData> {
    @Override
    protected void encode(ChannelHandlerContext ctx, CustomMsgData msg, ByteBuf out) throws Exception {
        int mainCmd = msg.getMainCmd();
        int subCmd = msg.getSubCmd();
        doEncode(mainCmd,subCmd,msg.getProtoBufData(),out);
    }

    private void doEncode(int mainCmd,int subCmd,byte[] protoBufData,ByteBuf out){
        byte[] bodyLen = BytesUtils.intToBytes(protoBufData.length);
        byte[] bytes = BytesUtils.intToBytes(mainCmd);
        byte[] bytes1 = BytesUtils.intToBytes(subCmd);
        out.writeBytes(bodyLen);
        out.writeBytes(bytes);
        out.writeBytes(bytes1);
        out.writeBytes(protoBufData);
    }

}
