package com.study.jdk5.util.concurrent.atomic.atomicinteger;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIncrementThread implements Runnable {
    public static AtomicInteger atomicnum = new AtomicInteger();
    @Override
    public void run() {
        incrementAtomic();
    }
    public void incrementAtomic(){
        atomicnum.getAndIncrement();
    }
}
