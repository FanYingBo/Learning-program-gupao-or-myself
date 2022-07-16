package com.study.selfs.jdk5.util.concurrent.semaphore;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 *
 * 令牌（计数信号量）
 * <p>
 *     用来控制同时访问某个特定资源的操作数量，或者同时执行某个指定操作的数量， 计数信号量还可以用来实现某种资源池，或者对容器施加边界
 * </p>
 * 关键概念： permit 许可   acquire 获取许可  release 释放许可
 * @see java.util.concurrent.CountDownLatch
 * @see java.util.concurrent.Semaphore
 */
public class SemaphoreDemo {

    private static ExecutorService executors = Executors.newFixedThreadPool(5);

    public static void main(String[] args) {
        // permits 信号量初始值（许可数量） 作为 非公平锁中的状态
        Semaphore semaphore = new Semaphore(5, true); // 非公平锁
        for(int i = 0;i < 10;i++){
            executors.submit(new TokenThread(i, semaphore));
        }
        executors.shutdown();
    }

}

class TokenThread implements Runnable {

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
            System.out.println("第 "+num+" 个线程，线程状态 "+Thread.currentThread().getState()+" 获取 令牌 剩余:"+semaphore.getQueueLength());
            Thread.sleep(1000);
            semaphore.release();
            System.out.println("第 "+num+" 个线程，线程状态 "+Thread.currentThread().getState()+" 释放 令牌");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
