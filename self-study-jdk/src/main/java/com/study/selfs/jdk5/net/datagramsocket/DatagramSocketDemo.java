package com.study.selfs.jdk5.net.datagramsocket;


import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

public class DatagramSocketDemo {

    public static void main(String[] args) {
        try {
            InetSocketAddress inetSocketAddress = new InetSocketAddress(9090);
            DatagramSocket datagramSocket = new DatagramSocket();
            datagramSocket.bind(inetSocketAddress);

        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

}
