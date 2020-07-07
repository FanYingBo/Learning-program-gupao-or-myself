package com.study.gupao.netty.http;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;

public abstract class HttpServerController {

    private static HttpServerMapping httpServerMapping;
    static{
        if(httpServerMapping == null){
            httpServerMapping = new HttpServerMapping();
        }
    }

    public void service(ChannelHandlerContext context, FullHttpRequest request){
        if("GET".equals(request.method().name())){
            context.writeAndFlush(doGet(request)).addListener(ChannelFutureListener.CLOSE);
        } else {
            context.writeAndFlush(doPost(request)).addListener(ChannelFutureListener.CLOSE);
        }
    }

    protected abstract FullHttpResponse doGet(FullHttpRequest request);
    protected abstract FullHttpResponse doPost(FullHttpRequest request);
}
