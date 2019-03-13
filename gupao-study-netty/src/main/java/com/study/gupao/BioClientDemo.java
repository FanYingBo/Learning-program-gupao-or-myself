package com.study.gupao;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class BioClientDemo {

    public static void main(String[] args) {

        try {
            Socket socket = new Socket("127.0.0.1",9991);
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(),true);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            printWriter.println("hello server &&");
            String s= bufferedReader.readLine();
            System.out.println(s);
            printWriter.close();
            bufferedReader.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
