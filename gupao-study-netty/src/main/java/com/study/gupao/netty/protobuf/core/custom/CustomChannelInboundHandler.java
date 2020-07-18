package com.study.gupao.netty.protobuf.core.custom;

import com.google.protobuf.InvalidProtocolBufferException;
import com.study.gupao.netty.protobuf.core.SimpleChannelInboundHandler;
import com.study.gupao.netty.protobuf.req.UserRequest;
import com.study.gupao.netty.protobuf.res.UserResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CustomChannelInboundHandler extends ChannelInboundHandlerAdapter {

    private static final Log log = LogFactory.getLog(SimpleChannelInboundHandler.class);
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof CustomMsgData){
            CustomMsgData msgData = (CustomMsgData)msg;
            // 这里可以根据main cmd 和 sub cmd 分发处理事件
            log.info("handler data main cmd " + msgData.getMainCmd() +" sub cmd "+msgData.getSubCmd());
            onMsgEvent(ctx,msgData.getMainCmd() , msgData.getSubCmd(), msgData.getProtoBufData());
        }else{
            log.error("unknown message type" + msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error(ctx.channel().remoteAddress() + " cause "+cause.getMessage());
    }



    private void onMsgEvent(ChannelHandlerContext ctx,int mainCmd,int subCmd, byte[] msgData){
        if(mainCmd == CMDConstants.MainCmdConstants.USER
                && subCmd == CMDConstants.UserSubCmdConstants.LOGIN){
            try {
                UserRequest.UserLogin messageLite = UserRequest.UserLogin.parseFrom(msgData);
                log.info("["+Thread.currentThread().getName()+"]  ["+ctx.channel().id()+"] Login info "+ messageLite.toString());
                UserResponse.LoginResult.Builder builder = UserResponse.LoginResult.getDefaultInstance().toBuilder();
                builder.setCode(0);
                builder.setToken("hhowqe21323qwwe123ojda==");
                ctx.writeAndFlush(new CustomMsgData(mainCmd ,subCmd ,builder.build()));
            } catch (InvalidProtocolBufferException e) {
                log.error("msg parse error" + e.getMessage());
            }
        }
    }

}
