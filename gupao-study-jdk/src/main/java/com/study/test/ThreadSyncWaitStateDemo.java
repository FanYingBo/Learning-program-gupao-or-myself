package com.study.test;

import com.study.test.child.Mobile;

import java.util.concurrent.CountDownLatch;

public class ThreadSyncWaitStateDemo {

    public static void main(String[] args) {
        // wait()阻塞线程 notify()唤醒线程测试
//        testThreadWait();
        // 死锁
        deadLocks();
    }

    private static void testThreadWait(){
        Thread1<Mobile> thread1 = new Thread1<>(Mobile.class);
        Thread2<Mobile> thread2 = new Thread2<>(Mobile.class);

        thread1.start();
        thread2.start();

        while(!(thread1.getState().equals(Thread.State.TERMINATED)&&thread2.getState().equals(Thread.State.TERMINATED))){
            System.out.println("Thread_1 status:" +thread1.getState());
            System.out.println("Thread_2 status:" +thread2.getState());
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void deadLocks(){
        Object obj1 = new Object();
        Object obj2 = new Object();
        Thread3 thread3 = new Thread3(obj1,obj2);
        Thread3 thread4 = new Thread3(obj2,obj1);
        thread3.start();
        thread4.start();
//
    }
}

class Thread1<T>  extends Thread{

    private Class<T> clazz;

    private T obj;

    public Thread1(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public void run() {
        synchronized (clazz){

            System.out.println("enter thread1....");
            System.out.println("thread1 is wait....");
            try {
//                Thread.sleep(1000); //
                clazz.wait();// 线程进入waiting 状态
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("thread1 is going on....");
            System.out.println("thread1 is over!!!");

        }
    }
}

class Thread2<T>  extends Thread{
    private Class<T> clazz;

    private T obj;

    public Thread2(Class<T> clazz) {
        this.clazz = clazz;
    }
    @Override
    public void run() {
        synchronized (clazz){

            System.out.println("enter thread2....");
            System.out.println("thread2 is sleep....");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            clazz.notify();
            System.out.println("thread2 is going on....");
            System.out.println("thread2 is over!!!");
        }

    }
}
class Thread3 extends Thread{

    private Object aClass;

    private Object aClass1;

    private CountDownLatch countDownLatch;
    public Thread3(Object aClass, Object aClass1) {
        this.aClass = aClass;
        this.aClass1 = aClass1;
    }

    @Override
    public void run() {
        synchronized (aClass){
            print("进入",aClass);
            print("离开",aClass);

            synchronized (aClass1){
                print("进入",aClass1);
                print("离开",aClass1);
            }
        }

    }

    private void print(String phase,Object clazz){
        System.out.printf("[线程 %s] %s %s \n",Thread.currentThread().getName(),phase,clazz);
    }
}
