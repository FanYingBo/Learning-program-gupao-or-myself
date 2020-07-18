package com.study.jdk5.util.concurrent.atomic.atomicreferencefieldupdater;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public class AtomicReferenceFieldUpdaterDemo {

    @Test
    public void testAtomicUpdate(){
        UpdateStatus updateStatus = new UpdateStatus(1);
        AtomicReferenceFieldUpdater<UpdateStatus,Integer> updater = AtomicReferenceFieldUpdater.newUpdater(UpdateStatus.class,Integer.class,"status");
        updater.getAndUpdate(updateStatus,t->2);
        System.out.println(updateStatus.getStatus());
    }

    /**
     * 在获取值得时候会通过Unsafe获取值，然后通过CAS进行替换
     */
    @Test
    public void concurrentUpdate(){
        UpdateStatus updateStatus = new UpdateStatus(1);
        AtomicReferenceFieldUpdater<UpdateStatus,Integer> updater = AtomicReferenceFieldUpdater.newUpdater(UpdateStatus.class,Integer.class,"status");
        for(int i = 0; i < 10;i++){
            Integer integer = new Integer(i);
            Thread thread = new Thread(()->{
                updater.getAndUpdate(updateStatus,t->integer);
                System.out.println("["+Thread.currentThread().getName()+"]"+" status "+ updateStatus.status);
            });
            thread.start();
        }
    }
}
