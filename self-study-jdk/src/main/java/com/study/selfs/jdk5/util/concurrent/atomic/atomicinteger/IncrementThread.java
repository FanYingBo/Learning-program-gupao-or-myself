package com.study.selfs.jdk5.util.concurrent.atomic.atomicinteger;

public class IncrementThread implements Runnable {

    public static int num = 0;


    @Override
    public void run() {
        increment();
    }

    public void increment(){
        num++;
    }

}
