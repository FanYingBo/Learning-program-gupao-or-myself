package com.study.gupao.netty.protobuf.core;


import com.study.gupao.netty.protobuf.req.FirstRequest;
import com.study.gupao.netty.protobuf.res.FirstResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.concurrent.TimeUnit;

/**
 * {@link io.netty.channel.DefaultChannelPipeline#fireChannelRead(Object)}
 * {@link io.netty.channel.AbstractChannelHandlerContext#invokeChannelRead(Object)}
 * {@link ChannelInboundHandlerAdapter#channelRead(ChannelHandlerContext, Object)}
 */
public class SimpleChannelInboundHandler extends ChannelInboundHandlerAdapter {

    private static final Log log = LogFactory.getLog(SimpleChannelInboundHandler.class);
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        FirstRequest.FirstReq req =  (FirstRequest.FirstReq)msg;
        String msg1 = req.getMsg();
        log.info("client ["+Thread.currentThread().getName()+"] request: requestId" +req.getReqId()+" msg "+msg1);
        FirstResponse.FirstRes.Builder res = FirstResponse.FirstRes.getDefaultInstance().toBuilder();
        res.setResMsg("Hello "+msg1);
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ctx.writeAndFlush(res.build());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error(ctx.channel().remoteAddress() + " cause "+cause.getMessage());
    }
}
