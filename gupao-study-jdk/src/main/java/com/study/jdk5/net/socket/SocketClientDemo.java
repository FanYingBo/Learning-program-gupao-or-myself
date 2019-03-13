package com.study.jdk5.net.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;

public class SocketClientDemo {

    public static void main(String[] args) {
        Socket socket = new Socket();
        try {
//            socket.setReuseAddress(true);//  立马与当前端口建立socket连接，即使前一个socket未接收数据，默认关闭
            socket.connect(new InetSocketAddress(SocketServerDemo.DEFAULT_PORT));

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
