package com.study.selfs.jdk8.util.concurrent.locks.stampedlock;

import org.junit.Test;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 1.StampedLock不支持重入
 *
 * 没错，StampedLock是不支持重入的，也就是说，在使用StampedLock时，不能嵌套使用，这点在使用时要特别注意。
 *
 * 2.StampedLock不支持条件变量
 *
 * 第二个需要注意的是就是StampedLock不支持条件变量，无论是读锁还是写锁，都不支持条件变量。
 * 3.StampedLock使用不当会导致CPU飙升 未证实
 * 4.锁升级和降级
 * @since 1.8
 * @see java.util.concurrent.locks.StampedLock
 */
public class StampedLockDemo {

    public static void main(String[] args) {
        Map<String, Long> collect = Arrays.asList(new String[]{"a", "a", "c", "d"}).stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        String key = collect.entrySet().stream().filter(stringLongEntry -> stringLongEntry.getValue() > 1).findFirst().get().getKey();
        System.out.println(key);
    }
    /**
     * 不支持condition
     */
    @Test
    public void testStampedLock1(){
        StampedLock stampedLock = new StampedLock();
        Lock lock = null;
        try {
            lock = stampedLock.asReadLock();
//             stampedLock.tryReadLock()
            lock.tryLock(10, TimeUnit.SECONDS);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            if(lock != null){
                lock.unlock();
            }
        }
    }
    @Test
    public void testStampedLock2(){
        StampedLock stampedLock = new StampedLock();
        long readLock = -1;
        try {
            readLock = stampedLock.tryReadLock(10, TimeUnit.SECONDS);
//             stampedLock.tryReadLock()

        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            stampedLock.unlockRead(readLock);
        }
    }
    @Test
    public void testStampedOptimisticLock(){
        StampedLock stampedLock = new StampedLock();
        long readLock = -1;
            readLock = stampedLock.tryOptimisticRead();
//             stampedLock.tryReadLock()

    }
    @Test
    public void testStampedLockCpuSoaring() throws InterruptedException {
        StampedLock stampedLock = new StampedLock();
        Thread thread1 = new Thread(() -> {
            long writeLock = -1;
                writeLock = stampedLock.writeLock();
                System.out.println("Thread 1 start .....");
                LockSupport.park();
        });
        thread1.start();
        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Thread thread2 = new Thread(() -> {
            long writeLock = -1;
                System.out.println("Thread 2 start .....");
                writeLock = stampedLock.readLock();
        });
        thread2.start();
        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread2.isInterrupted();
//        thread2.join();
    }

    @Test
    public void testReadWriteLock(){
        ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        Lock lock = readWriteLock.readLock();
        lock.tryLock();
    }

}
