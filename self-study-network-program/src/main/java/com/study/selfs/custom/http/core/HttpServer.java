package com.study.selfs.custom.http.core;

import com.study.selfs.custom.http.request.HttpCustomRequest;
import com.study.selfs.custom.http.response.HttpCustomResponse;
import com.study.selfs.custom.http.servlet.HttpCustomServlet;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {

    private int port;
    private ServerSocket serverSocket;
    private final ExecutorService executors = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private ServletMappingConfig servletMappingConfig;


    public HttpServer(int port){
        this.port = port;
        this.servletMappingConfig = new ServletMappingConfig();
        this.servletMappingConfig.init();
    }

    public void start(){
        try {
            this.serverSocket = new ServerSocket();
            this.serverSocket.bind(new InetSocketAddress(port),100);
            System.out.println("http server port "+ port+" have start");
            while(true){
                Socket socket = this.serverSocket.accept();
                HttpCustomRequest httpCustomRequest = new HttpCustomRequest(socket.getInputStream());
                HttpCustomResponse httpCustomResponse = new HttpCustomResponse(socket.getOutputStream());
                HttpCustomServlet servlet = this.servletMappingConfig.getServletByRequestUri(httpCustomRequest.getRequestUri());
                executors.execute(new HttpServletHandler(httpCustomRequest,httpCustomResponse,servlet));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
