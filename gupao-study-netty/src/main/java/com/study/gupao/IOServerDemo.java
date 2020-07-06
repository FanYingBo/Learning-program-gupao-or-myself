package com.study.gupao;

import com.study.gupao.format.StringBorderBuild;
import com.study.gupao.io.IOServer;

/**
 * {@link #createBIOServer() create a BIO server}
 * {@link #createNIOServer() create a NIO server}
 */
public class IOServerDemo {

    private static StringBorderBuild borderBuild = new StringBorderBuild("^","&");

    public static void main(String[] args) {
//        createBIOServer();
        createNettyNioServer();
    }

    public static void createNettyNioServer(){
        IOServer ioServer = ServerFactory.create(ServerFactory.IOServerKinds.NETTY_SERVER, 10231, null);
        Thread thread = new Thread(ioServer,"netty-nio-server-thread");
        thread.start();
    }

    public static void createBIOServer(){
        IOServer ioServer = ServerFactory.create(ServerFactory.IOServerKinds.BIO_SERVER,9991,borderBuild);
        Thread thread = new Thread(ioServer,"bio-server-thread");
        thread.start();
    }

    public static void createNIOServer(){
        IOServer ioServer = ServerFactory.create(ServerFactory.IOServerKinds.NIO_SERVER,8081,borderBuild);
        Thread thread = new Thread(ioServer,"nio-server-thread");
        thread.start();
    }
}
