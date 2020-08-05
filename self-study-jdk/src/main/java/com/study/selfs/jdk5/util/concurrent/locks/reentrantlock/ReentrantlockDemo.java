package com.study.selfs.jdk5.util.concurrent.locks.reentrantlock;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @since 1.5
 * @see java.util.concurrent.locks.Lock
 * @see java.util.concurrent.locks.ReentrantLock
 * @see java.util.concurrent.locks.AbstractQueuedSynchronizer
 * @see java.util.concurrent.locks.Condition
 */
public class ReentrantlockDemo {

    private static ReentrantLock lock = new ReentrantLock();

    private static Object monitorObject = new Object();

    private static Condition condition = lock.newCondition();

    private static ArrayList<Object> list = new ArrayList<>();

    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(3);
        List<Thread> threadList = new ArrayList<>();
        Thread thread = new Thread(){
            @Override
            public void run() {
                countDownLatch.countDown();
                lock.lock();
                try {
                    list.add("23");
                    list.add("32");
                    list.add("3233322");
                    list.add("32323");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        };
        Thread thread_2 = new Thread(){
            @Override
            public void run() {
                countDownLatch.countDown();
                lock.lock();
                try {
                    list.get(0);
                    list.get(1);
                    list.get(2);
                    list.get(3);
                }finally {
                    lock.unlock();
                }
            }
        };
        threadList.add(thread);
        threadList.add(thread_2);
        Thread thread_ = new Thread(){
            @Override
            public void run() {
                countDownLatch.countDown();
                testBlockThread();

            }
        };
        threadList.add(thread_);
        Thread thread_1 = new Thread(){
            @Override
            public void run() {
                countDownLatch.countDown();
                testBlockThread();
            }
        };
        threadList.add(thread_1);
        for(Thread thread1 :threadList){
            thread1.start();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (threadList.size() > 0){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Iterator<Thread> iterator = threadList.iterator();
            while(iterator.hasNext()){
                Thread next = iterator.next();
                System.out.println(next.getName()+" "+next.getState());
                if(next.getState() == Thread.State.TERMINATED){
                    iterator.remove();
                }
            }
        }

    }


    private static void testBlockThread(){
        synchronized (monitorObject){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
