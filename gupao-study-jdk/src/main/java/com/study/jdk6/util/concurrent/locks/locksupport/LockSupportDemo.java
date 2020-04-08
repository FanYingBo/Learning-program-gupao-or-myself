package com.study.jdk6.util.concurrent.locks.locksupport;


import java.util.concurrent.locks.LockSupport;

/**
 *
 * @since 1.6
 * @see java.util.concurrent.locks.LockSupport
 */
public class LockSupportDemo {

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(() -> {
            System.out.println("current thread1 start");
            LockSupport.park();
            System.out.println("current thread1 unpark");
        });
        thread1.start();
        Thread.sleep(1000);
        LockSupport.unpark(thread1);
    }
}
