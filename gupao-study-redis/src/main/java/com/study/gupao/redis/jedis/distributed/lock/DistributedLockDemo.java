package com.study.gupao.redis.jedis.distributed.lock;

/**
 *
 * 基于redis的一种分布式锁
 */
public class DistributedLockDemo {

    public static void main(String[] args) {
        DistributedLock distributedLock = new DistributedLock();

        Thread thread_1 = new Thread(new ThreadDemo(distributedLock, 1000),"Thread_1");
        Thread thread_2 = new Thread(new ThreadDemo(distributedLock, 1000),"Thread_2");
        thread_1.start();
        thread_2.start();
        try {
            thread_1.join();
            thread_2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}

class ThreadDemo implements Runnable{

    private DistributedLock distributedLock;

    private long sleepTime;

    public ThreadDemo(DistributedLock distributedLock, long sleepTime){
        this.distributedLock = distributedLock;
        this.sleepTime = sleepTime;
    }
    @Override
    public void run() {
        while(true){
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String identifier = distributedLock.acquireLock("thread", 3000, 3);
            if(identifier != null){
                System.out.println("thread: "+Thread.currentThread().getName()+" 获取锁: "+identifier);
            }
            if(distributedLock.releaseLockWithLua("thread", identifier)){
                System.out.println("thread: "+Thread.currentThread().getName()+" 释放锁: "+identifier);
            }

        }
    }
}
