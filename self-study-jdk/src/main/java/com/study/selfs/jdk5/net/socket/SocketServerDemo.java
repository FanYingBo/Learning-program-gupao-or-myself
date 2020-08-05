package com.study.selfs.jdk5.net.socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 */
public class SocketServerDemo {

    protected final static int DEFAULT_PORT = 9090;

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(DEFAULT_PORT);
            while(true){
                Socket socket = serverSocket.accept();
//                socket.setSendBufferSize();

//                socket.setOOBInline();// 默认情况java会忽视紧急数据需要设为true 开启
//                socket.sendUrgentData();// 发送紧急数据 紧急数据适当的形式放入正常的数据队列中
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String data = "";
                PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
                while((data=bufferedReader.readLine()) != null){

                    // 中断标记
                    if("".equals(data)){

                    }
                    // 处理 data
                    String response = processRequest(data);
                    // 返回处理结果响应
                    printWriter.write(response);
//                    printWriter.println(response);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String processRequest(String request){
        return "";
    }
}
