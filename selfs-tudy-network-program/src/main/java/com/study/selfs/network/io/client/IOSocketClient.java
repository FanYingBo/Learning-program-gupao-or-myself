package com.study.selfs.network.io.client;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;

public class IOSocketClient {

    public static void main(String[] args) throws InterruptedException, IOException {
        int count = 2;
        Socket socket = new Socket();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(8899);
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            socket.connect(inetSocketAddress);
            socket.setKeepAlive(true);
            for(int i = 0;i < count;i++){
                inputStream = socket.getInputStream();
                outputStream = socket.getOutputStream();
                SocketClient socketClient = new SocketClient(inputStream,outputStream);
                Thread thread  = new Thread(socketClient);
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

class SocketClient implements Runnable{

    private InputStream inputStream;

    private OutputStream outputStream;
    @Override
    public void run() {
        String msg = genMsg();
        print("发送", msg);
        this.write(msg);
        byte[] data = new byte[1024];
        int index = -1;
        String recmsg = "";
        while(index < 0){
            int read = this.read(data);
            if(read > -1){
                recmsg = new String(data);
                index = recmsg.indexOf("</service>");
            }else{
                index = -1;
            }
        }
        print("接收",recmsg);
        try {
            close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public SocketClient(Socket socket){
        try {
            this.inputStream = socket.getInputStream();
            this.outputStream = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void write(String msg){
        try {
            this.outputStream.write(msg.getBytes());
            this.outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int read(byte[] bytes){
        int len = -1;
        try {
            len = this.inputStream.read(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return len;
    }

    public SocketClient(InputStream inputStream,OutputStream outputStream){
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    private void close() throws IOException {
        this.outputStream.close();
        this.inputStream.close();
    }

    private String genMsg(){
        return "<client><sessionId>21212212</sessionId><data>jiiisdwwwd</data><thread>"+Thread.currentThread().getName()+"</thread></client>";
    }
    private void print(String type,String msg){
        System.out.printf("[线程：%s] socket客户端：%s\n",Thread.currentThread().getName(),toString());
        System.out.printf("[线程：%s] 客户端%s消息，%s\n",Thread.currentThread().getName(),type,msg);
    }
    @Override
    public String toString() {
        return "SocketClient{time="+System.currentTimeMillis()+
                ", inputStream=" + inputStream +
                ", outputStream=" + outputStream +
                '}';
    }
}


