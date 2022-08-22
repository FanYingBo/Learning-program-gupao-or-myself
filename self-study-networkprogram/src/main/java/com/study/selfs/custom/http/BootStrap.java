package com.study.selfs.custom.http;

import com.study.selfs.custom.http.core.HttpServer;

public class BootStrap {

    private static int port = 8080;

    public static void main(String[] args) {
        HttpServer httpServer = new HttpServer(port);
        httpServer.start();
    }
}
