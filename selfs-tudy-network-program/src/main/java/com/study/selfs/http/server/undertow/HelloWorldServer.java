package com.study.selfs.http.server.undertow;

import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;

import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 *
 *
 */
public class HelloWorldServer {

    private static Map<String,Handler> requestHandler = new HashMap<>();
    static{
        requestHandler.put("/hello",new RequestUriHandler());
    }
    public static void main(String[] args) {
        Undertow build = Undertow.builder().addHttpListener(8080, "localhost").setHandler(exchange ->{
            String requestURI = exchange.getRequestURI();
            Handler handler = requestHandler.get(requestURI);
            if(Objects.nonNull(handler)){
                Map<String, Deque<String>> queryParameters = exchange.getQueryParameters();
                Deque<String> strings = queryParameters.get("name");
                String returnString = handler.handleAndReturnString(strings.getFirst());
                exchange.getResponseSender().send(returnString);
            }
        }).build();

        build.start();
    }
}
