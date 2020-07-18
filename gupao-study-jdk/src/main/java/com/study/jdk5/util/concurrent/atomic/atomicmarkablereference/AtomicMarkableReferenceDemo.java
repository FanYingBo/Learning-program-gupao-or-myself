package com.study.jdk5.util.concurrent.atomic.atomicmarkablereference;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicMarkableReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *  版本控制
 */
public class AtomicMarkableReferenceDemo {
    AtomicMarkableReference<Integer> atomicMarkableReference ;
    volatile int count = 0;
    Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();
    @Before
    public void init(){
        atomicMarkableReference = new AtomicMarkableReference<>(0,Boolean.TRUE);
    }
    @Test
    public void testAtomicMarkable(){
        //  尝试去标记 为true
        boolean b = atomicMarkableReference.attemptMark(0, Boolean.TRUE);
        System.out.println(b);
        //weakCompareAndSet，这里会判断值和标记bool 如果不需要判断，则使用 set
        b = atomicMarkableReference.weakCompareAndSet(0,2,Boolean.TRUE,Boolean.TRUE);
        System.out.println(b);
    }

    /**
     * 顺序执行
     */
    @Test
    public void secMarkable(){
        for(int i = 1;i <= 10 ;i++){
            boolean bs = atomicMarkableReference.weakCompareAndSet(i - 1, i, Boolean.TRUE, Boolean.TRUE);
            System.out.println("["+Thread.currentThread().getName()+"]  num :" +i+" "+ bs);
        }
    }

    /**
     * 并发执行,线程执行的先后顺序不以言因此导致无法被替换
     */
    @Test
    public void concurrentMarkable(){
        int total = 10;
        for(int i = 1;i <= total;i++){
            Integer num = new Integer(i);
            Thread thread = new Thread(() -> {
                lock.lock();
                count ++;
                boolean bs = atomicMarkableReference.weakCompareAndSet(num - 1, num, Boolean.TRUE, Boolean.TRUE);
                System.out.println("["+Thread.currentThread().getName()+"] count: "+count+" num :" +num+" "+ bs);
                if(count == total){
                    condition.signal();
                }
                lock.unlock();
            },"thread-"+i);
            thread.start();
        }

        if(count != total){
            lock.lock();
            try {
                condition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.unlock();
        }
        System.out.println("count:"+count);
    }

}
