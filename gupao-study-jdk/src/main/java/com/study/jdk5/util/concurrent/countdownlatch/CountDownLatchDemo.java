package com.study.jdk5.util.concurrent.countdownlatch;


import java.util.concurrent.CountDownLatch;

/**
 *
 * @see java.util.concurrent.locks.AbstractQueuedSynchronizer 基于AQS框架
 * @see java.util.concurrent.Semaphore 令牌  并发
 * @see java.util.concurrent.CountDownLatch 并发
 *
 */
public class CountDownLatchDemo {

    public static void main(String[] args) throws InterruptedException {
        int count = 3;
        CountDownLatch countDownLatch = new CountDownLatch(count);
        for(int i = 0;i < count;i++){
            int in = i;
            Thread thread = new Thread(()->{
                if(in % 2 ==1){
                    countDownLatch.countDown();
                }else{
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    countDownLatch.countDown();
                }

                System.out.println("thread name:"+Thread.currentThread().getName());
            },"Thread_" + i);

            thread.start();
        }
        countDownLatch.await();
        System.out.println("主线程");
    }
}
