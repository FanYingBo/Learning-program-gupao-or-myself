package com.study.gupao.netty.http;

import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

public class HelloController extends HttpServerController{
    @Override
    public FullHttpResponse doGet(FullHttpRequest request) {
        FullHttpResponse httpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,HttpResponseStatus.OK,Unpooled.copiedBuffer("Hi, it's Netty HTTP Server", CharsetUtil.UTF_8));
        httpResponse.headers().add(HttpHeaderNames.CONTENT_TYPE,"text/html; charset=UTF-8");
        return httpResponse;
    }

    @Override
    public FullHttpResponse doPost(FullHttpRequest request) {
        return new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,HttpResponseStatus.OK);
    }
}
