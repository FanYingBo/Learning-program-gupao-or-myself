package com.study.gupao.io.bio;


import com.study.gupao.io.AbstractIOServer;
import com.study.gupao.io.IOServer;
import com.study.gupao.format.StringBorderBuild;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 */
public class BioServer extends AbstractIOServer implements IOServer {

    private static final Log log = LogFactory.getLog(BioServer.class);

    public final static int DEFAULT_SERVER_PORT = 7212;

    private final ExecutorService executors = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    private ServerSocket serverSocket;

    private AtomicLong atomicLong = new AtomicLong(1);
    private StringBorderBuild stringBorderBuild ;

    public BioServer(int port, StringBorderBuild stringBorderBuild){
        this.serverPort = port;
        this.stringBorderBuild = stringBorderBuild;
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
                executors.submit(new BioRequestHandler(socket, stringBorderBuild));
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
