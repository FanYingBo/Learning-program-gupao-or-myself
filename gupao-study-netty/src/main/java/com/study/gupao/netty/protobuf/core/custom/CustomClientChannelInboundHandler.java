package com.study.gupao.netty.protobuf.core.custom;

import com.study.gupao.netty.protobuf.core.SimpleChannelInboundHandler;
import com.study.gupao.netty.protobuf.req.UserRequest;
import com.study.gupao.netty.protobuf.res.UserResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.concurrent.TimeUnit;

public class CustomClientChannelInboundHandler extends ChannelInboundHandlerAdapter {
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

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        UserRequest.UserLogin.Builder builder = UserRequest.UserLogin.getDefaultInstance().toBuilder();
        builder.setLoginType(1);
        builder.setUserId(312);
        builder.setUserName("tom");
        ctx.writeAndFlush(new CustomMsgData(CMDConstants.MainCmdConstants.USER,CMDConstants.UserSubCmdConstants.LOGIN,builder.build()));
    }

    private void handlerMsg(ChannelHandlerContext ctx, CustomMsgData msgData){
        if(msgData.getMainCmd() == CMDConstants.MainCmdConstants.USER
                && msgData.getSubCmd() == CMDConstants.UserSubCmdConstants.LOGIN){
            try {
                UserResponse.LoginResult loginResult = msgData.parseTo(UserResponse.LoginResult.getDefaultInstance());
                log.info("Login result: "+loginResult);
                UserRequest.UserLogin.Builder builder = UserRequest.UserLogin.getDefaultInstance().toBuilder();
                builder.setLoginType(1);
                builder.setUserId(312);
                builder.setUserName("tom");
                TimeUnit.SECONDS.sleep(20);
                ctx.writeAndFlush(new CustomMsgData(msgData.getMainCmd(),msgData.getSubCmd(),builder.build()));
            } catch (Exception e) {
                log.error("msg parse error" + e.getMessage());
            }
        }
    }
}
