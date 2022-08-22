package com.study.selfs.custom.http.core;

import com.study.selfs.custom.http.servlet.HelloWorldServlet;
import com.study.selfs.custom.http.servlet.HttpCustomServlet;

import java.util.HashMap;
import java.util.Map;

public class ServletMappingConfig {

    private Map<String, HttpCustomServlet>  servletMap = new HashMap<>();

    public void init(){
        servletMap.put("/hello",new HelloWorldServlet());
    }

    public HttpCustomServlet getServletByRequestUri(String requestUri){
        return servletMap.get(requestUri);
    }
}
