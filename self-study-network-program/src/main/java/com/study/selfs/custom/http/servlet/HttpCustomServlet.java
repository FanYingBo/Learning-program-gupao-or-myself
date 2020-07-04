package com.study.selfs.custom.http.servlet;

import com.study.selfs.custom.http.request.HttpCustomRequest;
import com.study.selfs.custom.http.response.HttpCustomResponse;

import java.io.IOException;

public abstract class HttpCustomServlet {

    public void service(HttpCustomRequest request, HttpCustomResponse response) throws Exception{
        if("GET".equals(request.getRequestMethod())){
            doGet(request,response);
        }else{
            doPost(request,response);
        }
    }

    public abstract void doGet(HttpCustomRequest request, HttpCustomResponse response) throws Exception;

    public abstract void doPost(HttpCustomRequest request, HttpCustomResponse response) throws Exception;

}
