package com.study.selfs.custom.http.servlet;

import com.study.selfs.custom.http.annotation.WebServlet;
import com.study.selfs.custom.http.request.HttpCustomRequest;
import com.study.selfs.custom.http.response.HttpCustomResponse;

import java.io.IOException;

@WebServlet(path = "/hello")
public class HelloWorldServlet extends HttpCustomServlet{

    @Override
    public void doGet(HttpCustomRequest request, HttpCustomResponse response) throws IOException {
        String read = request.read();
        System.out.println(read);
        write(response);
    }

    @Override
    public void doPost(HttpCustomRequest request, HttpCustomResponse response) throws Exception {

    }

    private void write(HttpCustomResponse response) throws IOException {
        response.write("custom servlet");
    }


}
