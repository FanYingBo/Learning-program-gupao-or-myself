package com.study.gupao.netty.http;

import java.util.HashMap;
import java.util.Map;

public class HttpServerMapping {

    private Map<String ,HttpServerController> httpServerControllerMap = new HashMap<>();

    public HttpServerMapping(){
        init();
    }

    public void init(){
        httpServerControllerMap.put("/hello",new HelloController());
    }

    public HttpServerController get(String uri){
        return httpServerControllerMap.get(uri);
    }

}
