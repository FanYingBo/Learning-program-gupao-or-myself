package com.study.selfs.custom.http.core;

import com.study.selfs.custom.http.request.HttpCustomRequest;
import com.study.selfs.custom.http.response.HttpCustomResponse;
import com.study.selfs.custom.http.servlet.HttpCustomServlet;

import java.io.IOException;


public class HttpServletHandler implements Runnable{

    private HttpCustomRequest httpCustomRequest;
    private HttpCustomServlet httpCustomServlet;
    private HttpCustomResponse httpCustomResponse;

    public HttpServletHandler(HttpCustomRequest httpServletRequest, HttpCustomResponse httpCustomResponse,HttpCustomServlet httpCustomServlet) {
        this.httpCustomServlet = httpCustomServlet;
        this.httpCustomRequest = httpServletRequest;
        this.httpCustomResponse = httpCustomResponse;
    }

    @Override
    public void run() {
        try{
            httpCustomServlet.service(this.httpCustomRequest,this.httpCustomResponse);
        }catch (Exception e){
            e.printStackTrace();
            if(e.getMessage().equals("400")){
                try {
                    this.httpCustomResponse.write("HTTP/1.1 400 Bad Request\n");
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }finally {
            try {
                this.httpCustomRequest.close();
                this.httpCustomResponse.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
