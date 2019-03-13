package com.study.jdk5.util.concurrent.atomic.atomicinteger;

public class AtomicIntegerDemo {



    public static void main(String[] args) throws InterruptedException {
//        AtomicInteger atomicInteger = new AtomicInteger();
//        System.out.println(atomicInteger.get());// 0
        ThreadPoolListener listener = new ThreadPoolListener();
        long start = System.currentTimeMillis();
        for(int i = 0;i < 1000;i++){
            IncrementThread incrementThread = new IncrementThread();
            Thread thread = new Thread(incrementThread);
            listener.threadList.add(thread);
            thread.start();
        }
        listener.listenThread();
        long end = System.currentTimeMillis();
        System.out.println("非原子增加 耗时："+(end-start)+" 结果："+IncrementThread.num);
        ThreadPoolListener listener_ = new ThreadPoolListener();
        start = System.currentTimeMillis();
        for(int i = 0;i < 1000;i++){
            AtomicIncrementThread incrementThread = new AtomicIncrementThread();
            Thread thread = new Thread(incrementThread);
            listener_.threadList.add(thread);
            thread.start();
        }
        listener_.listenThread();
        end = System.currentTimeMillis();
        System.out.println("原子增加 耗时："+(end-start)+" 结果："+AtomicIncrementThread.atomicnum);
    }
}


