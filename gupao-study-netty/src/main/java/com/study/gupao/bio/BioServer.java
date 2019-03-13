package com.study.gupao.bio;


import com.study.gupao.AbstractIOServer;
import com.study.gupao.IOServer;
import com.study.gupao.format.StringBorderBuild;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class BioServer extends AbstractIOServer implements IOServer {

    public final static int DEFAULT_SERVER_PORT = 7212;

    private final ExecutorService executors = Executors.newFixedThreadPool(10);

    private ServerSocket serverSocket;

    private AtomicLong atomicLong = new AtomicLong(1);

    public BioServer(int port, StringBorderBuild stringBorderBuild){
        this.serverPort = port;
    }

    @Override
    public void start() {
        try {
            serverSocket = new ServerSocket(this.serverPort);
            isStart = true;
            log.info("The bio server has been created and bind port "+this.serverPort);
            while(true){
                Socket socket = serverSocket.accept();
                // 不使用线程池 1:1
//                Thread thread = new Thread(new BioRequestHandler(socket),
//                        "Thread-bio-"+atomicLong.getAndIncrement());
//                thread.start();
                // 使用线程池 m:n
                executors.submit(new BioRequestHandler(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.error("A exception happen when start the server:"+e.getMessage());
        }
    }

    @Override
    public boolean close() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            return false;
        }
        isStart = false;
        return true;
    }

    @Override
    public void run() {
        start();
    }
}
