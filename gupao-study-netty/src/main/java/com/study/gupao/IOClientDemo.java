package com.study.gupao;

import com.study.gupao.format.StringBorderBuild;
import com.study.gupao.io.bio.BioClient;
import com.study.gupao.io.nio.NioClient;
import com.study.gupao.netty.protobuf.CustomProtoBufNettyClient;
import com.study.gupao.netty.protobuf.ProtoBufNettyClient;

public class IOClientDemo {
    private static StringBorderBuild borderBuild = new StringBorderBuild("^","&");
    private static int thread_count = 20;
    public static void main(String[] args) {
//        nioClientDemo();
//        ioClientDemo();
//        nettyNioClientDemo();
        nettyCustomProtoBufClientDemo();
    }

    public static void nettyCustomProtoBufClientDemo(){
        Thread thread = new Thread(new CustomProtoBufNettyClient(11028),"netty-client-thread");
        thread.start();
    }
    public static void nettyNioClientDemo(){
        for(int count = 0;count < thread_count;count++){
            Thread thread = new Thread(new ProtoBufNettyClient(10231),"netty-client-thread_"+count);
            thread.start();
        }
    }

    public static void nioClientDemo(){
        Thread thread = new Thread(new NioClient(8081, borderBuild),"client-thread_first");
        Thread thread_1 = new Thread(new NioClient(8081, borderBuild),"client-thread_second");
        Thread thread_2 = new Thread(new NioClient(8081, borderBuild),"client-thread_third");
        Thread thread_3 = new Thread(new NioClient(8081, borderBuild),"client-thread_forth");
        thread.start();
        thread_1.start();
        thread_2.start();
        thread_3.start();
    }

    public static void ioClientDemo(){
        Thread thread = new Thread(new BioClient(9991, borderBuild),"client-thread_first");
//        Thread thread_1 = new Thread(new BioClient(9991, borderBuild),"client-thread_second");
//        Thread thread_2 = new Thread(new BioClient(9991, borderBuild),"client-thread_third");
//        Thread thread_3 = new Thread(new BioClient(9991, borderBuild),"client-thread_forth");
        thread.start();
//        thread_1.start();
//        thread_2.start();
//        thread_3.start();
    }
}
