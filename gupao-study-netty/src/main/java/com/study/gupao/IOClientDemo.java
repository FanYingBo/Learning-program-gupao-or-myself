package com.study.gupao;

import com.study.gupao.format.StringBorderBuild;
import com.study.gupao.io.bio.BioClient;
import com.study.gupao.io.nio.NioClient;
import com.study.gupao.netty.NettyClient;

public class IOClientDemo {
    private static StringBorderBuild borderBuild = new StringBorderBuild("^","&");
    public static void main(String[] args) {
//        nioClientDemo();
//        ioClientDemo();
        nettyNioClientDemo();
    }

    public static void nettyNioClientDemo(){
        Thread thread = new Thread(new NettyClient(10231),"netty-client-thread_first");
        thread.start();
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
