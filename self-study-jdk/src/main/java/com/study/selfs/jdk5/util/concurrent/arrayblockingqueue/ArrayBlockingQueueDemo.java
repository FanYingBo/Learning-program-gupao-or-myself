package com.study.selfs.jdk5.util.concurrent.arrayblockingqueue;


import com.study.selfs.thread.PutThread;
import com.study.selfs.thread.TakeThread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;

/**
 * 1.有容量大小限制
 * 2.增加的方法：put 超过容量上限调用Condition.await使线程处于等待（WAITING）阻塞状态
 *   add 调用offer
 *   offer使用重入锁ReentrantLock
 * 3.取值的方法：peek 获得一个值 使用重入锁ReentrantLock
 * 4.删除：poll 取出一个值 使用重入锁ReentrantLock
 *        take 若队列大小为0 则处于等待（WAITING）阻塞状态
 *        remove 删除 无返回值
 * @since java5
 * @see java.util.concurrent.ArrayBlockingQueue
 */
public class ArrayBlockingQueueDemo {

    public static void main(String[] args) throws InterruptedException {
        ArrayBlockingQueue arrayBlockingQueue = new ArrayBlockingQueue(4);
        CountDownLatch countDownLatch = new CountDownLatch(2);
        // 放入队列的方法用offer的话，生产比消费快会导致有一部分数据被丢弃
        PutThread putThread = new PutThread(arrayBlockingQueue, countDownLatch, 4);// 取出数据线程
        TakeThread takeThread = new TakeThread(arrayBlockingQueue,countDownLatch,4);// 取出数据线程
        putThread.start();
        takeThread.start();
        countDownLatch.await();
    }
}


