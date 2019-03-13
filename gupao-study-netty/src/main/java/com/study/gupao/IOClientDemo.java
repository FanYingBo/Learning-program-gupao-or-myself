package com.study.gupao;

import com.study.gupao.format.StringBorderBuild;
import com.study.gupao.nio.NioClient;

public class IOClientDemo {
    private static StringBorderBuild borderBuild = new StringBorderBuild("^","&");
    public static void main(String[] args) {

        Thread thread = new Thread(new NioClient(8081, borderBuild),"nio-client-thread_first");
        Thread thread_1 = new Thread(new NioClient(8081, borderBuild),"nio-client-thread_second");
        Thread thread_2 = new Thread(new NioClient(8081, borderBuild),"nio-client-thread_third");
        Thread thread_3 = new Thread(new NioClient(8081, borderBuild),"nio-client-thread_forth");
        thread.start();
        thread_1.start();
        thread_2.start();
        thread_3.start();
    }
}
