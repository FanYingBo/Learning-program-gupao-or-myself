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
//        createNIOServer();
//        createBIOServer();
//        createNettyProtoBufNioServer();
//        createNettyHttpServer();
        createCustomNettyProtoBufServer();
    }

    public static void createCustomNettyProtoBufServer(){
        IOServer ioServer = ServerFactory.create(ServerFactory.IOServerKinds.CUSTOM_PROTO_NETTY_SERVER, 11028, null);
        ioServer.run();
    }
    /**
     * 构建 Netty http 服务端
     */
    public static void createNettyHttpServer(){
        IOServer ioServer = ServerFactory.create(ServerFactory.IOServerKinds.HTTP_NETTY_SERVER, 8083, null);
        Thread thread = new Thread(ioServer,"netty-nio-server-thread");
        thread.start();
    }
    /**
     * 构建 Netty protobuf 服务端
     */
    public static void createNettyProtoBufNioServer(){
        IOServer ioServer = ServerFactory.create(ServerFactory.IOServerKinds.PROTOBUF_NETTY_SERVER, 10231, null);
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
