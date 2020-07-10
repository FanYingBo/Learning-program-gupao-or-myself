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
            handlerMsg(ctx,msgData);
        }else{
            log.error("unknown message type" + msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error(ctx.channel().remoteAddress() + " cause "+cause.getMessage());
    }

    private void handlerMsg(ChannelHandlerContext ctx,CustomMsgData msgData){
        if(msgData.getMainCmd() == CMDConstants.MainCmdConstants.USER
                && msgData.getSubCmd() == CMDConstants.UserSubCmdConstants.LOGIN){
            try {
                UserRequest.UserLogin messageLite = msgData.parseTo(UserRequest.UserLogin.getDefaultInstance());
                log.info("Login info "+ messageLite.toString());
                UserResponse.LoginResult.Builder builder = UserResponse.LoginResult.getDefaultInstance().toBuilder();
                builder.setCode(0);
                builder.setToken("hhowqe21323qwwe123ojda==");
                ctx.writeAndFlush(new CustomMsgData(msgData.getMainCmd(),msgData.getSubCmd(),builder.build()));
            } catch (InvalidProtocolBufferException e) {
                log.error("msg parse error" + e.getMessage());
            }
        }
    }

}
