package com.study.gupao.netty.core;

import com.study.gupao.netty.req.FirstRequest;
import com.study.gupao.netty.res.FirstResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.concurrent.atomic.AtomicLong;

public class SimpleClientChannelInboundHandler extends ChannelInboundHandlerAdapter {

    private AtomicLong atomicLong = new AtomicLong(1);
    private Log log = LogFactory.getLog(SimpleClientChannelInboundHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        FirstResponse.FirstRes res =  (FirstResponse.FirstRes)msg;
        log.info("server response "+ res.getResMsg());
        write(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        write(ctx);
    }

    private void write(ChannelHandlerContext ctx){
        FirstRequest.FirstReq.Builder first = FirstRequest.FirstReq.getDefaultInstance().toBuilder();
        first.setReqId(atomicLong.getAndIncrement());
        first.setMsg("tom");
        ctx.writeAndFlush(first.build());
    }
}
