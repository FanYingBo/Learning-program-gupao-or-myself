package com.study.jdk5.lang.thread;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.*;

/**
 *
 *@see #verifyAllThreadState() 线程状态演示
 *@see #interruptState01() 线程中断演示
 *@see #interruptState02() 线程中断和激活 interrupted
 *@see ThreadAct
 *@see java.lang.Thread
 *@see java.lang.Thread.State
 *@see java.util.concurrent.locks.LockSupport
 * */
public class ThreadDemo {

//    public static ReentrantLock globalLock = new ReentrantLock(true);
    public static void main(String[] args) throws InterruptedException {
        // 线程状态
//        verifyAllThreadState();
        // 线程中断
//        interruptState01();

//        interruptState02();

        interruptState03();

//        Thread thread = new Thread(() -> {
//            while(true){
//
//            }
//        });
//        thread.start();
//        thread.join();
//        Thread thread_ = new Thread(() -> {
//            System.out.println("Thread2");
//        });
//        thread_.start();
    }


    private static void interruptState02() {
        Thread thread = new Thread(() -> {
            while(true){
                boolean interrupted = Thread.currentThread().isInterrupted();
                if(interrupted){
                    System.out.println(Thread.currentThread().isInterrupted());
                    // 激活线程
                    Thread.interrupted();
                    System.out.println(Thread.currentThread().isInterrupted());
                }
            }
        });
        thread.start();
        thread.interrupt();
    }

    private static void interruptState01() throws InterruptedException {
        Thread thread = new Thread(() -> {
            int i = 0;
            while(!Thread.currentThread().isInterrupted()){
                i++;
            }
            System.out.println(i);
        });
        thread.start();
        thread.interrupt();
        System.out.println(thread.isInterrupted());
    }

    private static void interruptState03() throws InterruptedException {
        Thread thread = new Thread(() -> {
            while(true){
                try {
                    Thread.sleep(10000); // sleep 结束后会先复位当前线程，类似Thread.interrupted
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        System.out.println("before : " + thread.isInterrupted());
        thread.interrupt();
        System.out.println("after : " + thread.isInterrupted());
    }

    private static List<ThreadAct> threads = new ArrayList<>();
    private static int count = 5;

    public static void verifyAllThreadState(){
        CountDownLatch countDownLatch = new CountDownLatch(count);
        for(int i = 0;i < count;i++){
            // 1.定义count数量的线程
            processThreads(i);
        }
        // 2.启动线程
        for(ThreadAct thread:threads){
            print("New",thread);
            countDownLatch.countDown();
            thread.start();
            print("Dyn",thread);
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 3.通过轮询不断获取线程状态
        doListenThreads();
    }


    private static void processThreads(int i){
        ThreadAct threadAct = new ThreadAct("threadAct_"+i);
        threads.add(threadAct);
    }
    private static void doListenThreads(){
        while(true){
            Iterator<ThreadAct> iterator = threads.iterator();
            while(iterator.hasNext()){
                ThreadAct next = iterator.next();
                print("ItDyn",next);
                if(!next.isAlive()){
                    iterator.remove();
                }
                //主线程等待一段时间，UNPARK 激活线程使线程进入Runnable状态
                LockSupport.unpark(next);
            }
            if(threads.isEmpty()){
                System.out.println(ThreadAct.a);
                break;
            }
        }
    }


    protected static void print(String state,ThreadAct thread){
        System.out.printf("%s [线程 %s] 状态 %s a的值 %d \n",state,thread.getName(),thread.getState(),thread.a);
    }
}
class ThreadAct extends Thread{
    public static int a = 0;

    // 全局lock 类似于synchronized(Class.class) 但不会令线程进入阻塞状态
    public static Lock lock = new ReentrantLock(true);


    public static ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    public static Lock readLock =  readWriteLock.readLock();
    public static Lock writeLock = readWriteLock.writeLock();
    public ThreadAct(String threadName){
        super(threadName);
    }

    public void run(){
        // 线程阻塞进入TIMED_WAITING 状态
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        ThreadDemo.print("in",Thread.currentThread());
        // synchronized 使线程进入阻塞状态BLOCKED
//        synchronized (ThreadAct.class) {
//        LockSupport.park();

        lock.lock();
            for (; a < 100000000; ) {
                plus();
            }
//        }
        lock.unlock();
        // 线程挂起，进入WAITING状态
//        LockSupport.park();
    }

    private void plus(){
        a++;
    }

}
