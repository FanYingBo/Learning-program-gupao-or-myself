package com.study.selfs.network.io.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class IOSocketServer {

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(8899);
        Socket socket = null;
        System.out.println("端口8899服务已启动....");
        while (true) {
            try {

                socket = server.accept();
                socket.setKeepAlive(true);
                SocketServerThread socketServerThread = new SocketServerThread(socket);
                Thread thread = new Thread(socketServerThread);
                thread.start();
            } catch (IOException e) {
                if (socket != null) {
                    socket.close();
                }
            }
        }
    }

    protected static String formate(String msg){
        msg = msg.replace("client","service");
        return msg;
    }

}

class SocketServerThread implements Runnable{

    private Socket socket;

    private InputStream inputStream;

    private OutputStream outputStream;

    @Override
    public void run() {
        try {
            byte[] data = new byte[1024];
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
            byte[] bytes = new byte[1024];
            int datalen = 0;
            int len = 0;
            while(isConnected() && (len = inputStream.read(bytes)) != -1){
                System.arraycopy(bytes,0,data,datalen,len);
                datalen+=len;
                // 以一定的格式接收数据<client><service></service></client>
                String msg = new String(data);
                int index = msg.indexOf("</client>");
                if(index > 0){
                    print("接收",msg);
                    String formate = IOSocketServer.formate(msg);
                    outputStream.write(formate.getBytes());
                    print("发送",formate);
                    outputStream.flush();
                    datalen = 0;
                    bytes = new byte[1024];
                    data = new byte[1024];
                }
            }

//            outputStream.close();
//            inputStream.close();
//            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    public SocketServerThread(Socket socket){
        this.socket = socket;
    }

    public boolean isConnected(){
        return socket.isConnected();
    }
    private void close() throws IOException {
        if(outputStream != null){
            this.outputStream.close();
        }
        if(inputStream != null){
            this.inputStream.close();
        }
        if(socket != null){
            this.socket.close();
        }
    }
    private void print(String type,String msg){
        System.out.printf("[线程：%s] 服务端%s消息，%s\n",Thread.currentThread().getName(),type,msg);
    }

}

