package com.study.gupao.netty.protobuf.core.custom;

import com.study.gupao.netty.protobuf.core.SimpleChannelInboundHandler;
import com.study.gupao.netty.protobuf.req.UserRequest;
import com.study.gupao.netty.protobuf.res.UserResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class CustomClientChannelInboundHandler extends ChannelInboundHandlerAdapter {
    private static final Log log = LogFactory.getLog(SimpleChannelInboundHandler.class);
    private AtomicInteger atomicInteger = new AtomicInteger(1);
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof CustomMsgData){
            CustomMsgData msgData = (CustomMsgData)msg;
            // 这里可以根据main cmd 和 sub cmd 分发处理事件
            log.info("["+Thread.currentThread().getName()+"] ["+ctx.channel().id()+"]handler data main cmd " + msgData.getMainCmd() +" sub cmd "+msgData.getSubCmd());
            onMsgEvent(ctx,msgData.getMainCmd() , msgData.getSubCmd(), msgData.getProtoBufData());
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
        builder.setUserId(atomicInteger.incrementAndGet());
        builder.setUserName("tom");
        ctx.writeAndFlush(new CustomMsgData(CMDConstants.MainCmdConstants.USER,CMDConstants.UserSubCmdConstants.LOGIN,builder.build()));
    }

    private void onMsgEvent(ChannelHandlerContext ctx, int mainCmd,int subCmd, byte[] msgData){
        if(mainCmd== CMDConstants.MainCmdConstants.USER
                && mainCmd == CMDConstants.UserSubCmdConstants.LOGIN){
            try {
                UserResponse.LoginResult loginResult = UserResponse.LoginResult.parseFrom(msgData);
                log.info("Login result: "+loginResult);
                UserRequest.UserLogin.Builder builder = UserRequest.UserLogin.getDefaultInstance().toBuilder();
                builder.setLoginType(1);
                builder.setUserId(atomicInteger.incrementAndGet());
                builder.setUserName("tom");
                TimeUnit.SECONDS.sleep(20);
                ctx.writeAndFlush(new CustomMsgData(mainCmd,subCmd,builder.build()));
            } catch (Exception e) {
                log.error("msg parse error" + e.getMessage());
            }
        }
    }
}
