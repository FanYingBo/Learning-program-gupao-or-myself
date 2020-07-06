package com.study.gupao.netty.core;


import com.study.gupao.netty.req.FirstRequest;
import com.study.gupao.netty.res.FirstResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SimpleChannelInboundHandler extends ChannelInboundHandlerAdapter {

    private static final Log log = LogFactory.getLog(SimpleChannelInboundHandler.class);
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        FirstRequest.FirstReq req =  (FirstRequest.FirstReq)msg;
        String msg1 = req.getMsg();
        log.info("client request: requestId" +req.getReqId()+" msg "+msg1);
        FirstResponse.FirstRes.Builder res = FirstResponse.FirstRes.getDefaultInstance().toBuilder();
        res.setResMsg("Hello "+msg1);
        ctx.writeAndFlush(res.build());
    }
}
