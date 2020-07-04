package com.study.gupao.io.bio;


import com.study.gupao.io.AbstractIOServer;
import com.study.gupao.io.IOServer;
import com.study.gupao.format.StringBorderBuild;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

/**
 *  服务端TCP keepalive连接参数
 * /proc/sys/net/ipv4/tcp_keepalive_time  7200  发送心跳（ACK）的周期时间
 * /proc/sys/net/ipv4/tcp_keepalive_probes 9  没有收到心跳时继续发送ACK的次数
 * /proc/sys/net/ipv4/tcp_keepalive_intvl 75  没有收到心跳时继续发送ACK的频率（时间间隔）
 *
 * TCP服务端相关参数
 * 参数名 默认值 简介
 * proc/sys/net/ipv4/tcp_max_syn_backlog 1024(不同系统不一样) 服务器端在接受客户端的SYN请求之后，在记忆领域可存储的客户端SYN请求数
 * proc/sys/net/ipv4/tcp_synack_retries 5(默认) 服务器端处于SYN_RECV状态之后，在一定时间没有收到客户端的SYN时，再次发送SYN ACK的次数
 * proc/sys/net/ipv4/tcp_abort_on_overflow 0(默认无效) 当服务器端高负荷时，给客户端发送RST（Connect reset）切断连接来保护服务器
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
            serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(this.serverPort),100);
            serverSocket.setReceiveBufferSize(9 * 1024);
            isStart = true;
            log.info("The bio server has been created and bind port "+this.serverPort);
            while(true){
                Socket socket = serverSocket.accept();
                // 不使用线程池 1:1
//                Thread thread = new Thread(new BioRequestHandler(socket),
//                        "Thread-bio-"+atomicLong.getAndIncrement());
//                thread.start();
                // 使用线程池 m:n
                socket.setKeepAlive(true);
                socket.setReceiveBufferSize(9 * 1024);
                socket.setSendBufferSize(9 * 1024);
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
