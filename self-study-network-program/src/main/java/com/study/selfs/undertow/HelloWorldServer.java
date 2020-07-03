package com.study.selfs.undertow;

import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;

/**
 *
 *
 */
public class HelloWorldServer {

    public static void main(String[] args) {
        Undertow build = Undertow.builder().addHttpListener(8080, "localhost").setHandler(new HttpHandler() {
            @Override
            public void handleRequest(HttpServerExchange exchange) throws Exception {
                exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");

                exchange.getResponseSender().send("hello undertow");
            }
        }).build();

        build.start();
    }
}
