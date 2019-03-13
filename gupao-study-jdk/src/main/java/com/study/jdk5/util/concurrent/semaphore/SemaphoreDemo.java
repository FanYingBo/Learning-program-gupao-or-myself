package com.study.jdk5.util.concurrent.semaphore;


import java.util.concurrent.Semaphore;

/**
 *
 * 令牌
 * @see java.util.concurrent.CountDownLatch
 * @see java.util.concurrent.Semaphore
 */
public class SemaphoreDemo {

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(5);
        for(int i = 0;i < 10;i++){
            TokenThread tokenThread = new TokenThread(i,semaphore);
            tokenThread.start();
        }
    }

}

class TokenThread extends Thread{

    private int num;

    private Semaphore semaphore;

    public TokenThread(int num , Semaphore semaphore) {
        this.num = num;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        try {
            semaphore.acquire();
            System.out.println("第 "+num+" 个线程，线程状态 "+currentThread().getState()+" 获取 令牌");
            Thread.sleep(1000);
            semaphore.release();
            System.out.println("第 "+num+" 个线程，线程状态 "+currentThread().getState()+" 释放 令牌");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
