package com.study.selfs.jdk5.util.concurrent.concurrentlinkedqueue;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;

/**
 *
 */
public class ConcurrentLinkedQueueDemo {

    public static void main(String[] args) throws InterruptedException {
        int workThreadCount = 10;
        ConcurrentLinkedQueue concurrentLinkedQueue = new ConcurrentLinkedQueue();
        CountDownLatch countDownLatch = new CountDownLatch(2);
        Thread putThread = new Thread(() -> {
            countDownLatch.countDown();
            for (int count = 0;count < workThreadCount;count++) {
                new Thread(()-> {
                    concurrentLinkedQueue.offer(System.currentTimeMillis());
                }).start();
            }
        });

//        putThread.join();
        Thread takeThread = new Thread(() -> {
            countDownLatch.countDown();
            for (int count = 0;count < workThreadCount;count++) {
                new Thread(()-> {
                    Object poll = concurrentLinkedQueue.poll();
                    System.out.println("poll: "+poll);
                }).start();
            }
        });
        putThread.start();
        takeThread.start();
        countDownLatch.await();

    }
}
