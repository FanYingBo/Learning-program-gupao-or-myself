package com.study.test;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.TimeUnit;

/**
 * 死锁，与死锁检查
 */
public class DeadLockDemo {

    private static Object lock1 = new Object();
    private static Object lock2 = new Object();

    public static void main(String[] args) {
        deadLock();
        checkDeadLock();
    }

    /**
     * 1.通过ManagementFactory.getThreadMXBean() 获取当前的线程MXBean
     * 2.根据线程ID和锁等待的对象，查看线程死锁信息
     *
     */
    private static void checkDeadLock(){
        Thread checkThread = new Thread(()->{
            while(true){
                ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
                long[] deadlockedThreads = threadMXBean.findDeadlockedThreads();
                if(deadlockedThreads != null){
                    for(long threadId : deadlockedThreads){
                        ThreadInfo threadInfo = threadMXBean.getThreadInfo(threadId);
                        // 这里是获取现在阻塞时候，锁等待的对象
                        System.out.println("当前线程ID："+threadInfo.getThreadId()+" lock:"+threadInfo.getLockInfo());
                        System.out.println("锁的拥有者："+threadInfo.getLockOwnerId());
                    }
                }
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        checkThread.start();
    }

    private static void deadLock(){
        Thread thread = new Thread(()->{
            synchronized (lock1){
                System.out.printf("[%d] 拥有的锁 "+lock1+"\n",Thread.currentThread().getId());
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lock2){
                }
            }
        });
        Thread thread1 = new Thread(()->{
            synchronized (lock2){
                System.out.printf("[%d] 拥有的锁 "+lock2+"\n",Thread.currentThread().getId());
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lock1){
                }
            }
        });
        thread.start();
        thread1.start();
    }
}
