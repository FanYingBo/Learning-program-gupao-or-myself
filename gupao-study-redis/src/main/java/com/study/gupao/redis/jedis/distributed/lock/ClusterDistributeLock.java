package com.study.gupao.redis.jedis.distributed.lock;

import java.util.concurrent.CountDownLatch;

/**
 * redis 集群 多线程并发访问下的分布式锁测试
 */
public class ClusterDistributeLock {

    public static void main(String[] args) {
        testMultiThread();
//        testPerThread();
    }

    public static  void testPerThread() {
        RedisClusterDistributedLock distributedLock = new RedisClusterDistributedLock();
        String order = distributedLock.acquireLock("order", 2000, 20);
        System.out.println("获取锁 "+ order);
        boolean order1 = distributedLock.releaseLock("order", order, 2000);
        System.out.println("释放锁 "+order1);
    }

    /**
     * redis 多线程访问下的分布式锁
     */
    public static void testMultiThread() {
        int count = 10;
        CountDownLatch countDownLatch = new CountDownLatch(count);
        for(int i = 0; i< count;i++){
            new Thread(()->{
                countDownLatch.countDown();
                RedisClusterDistributedLock distributedLock = new RedisClusterDistributedLock();
                String order = distributedLock.acquireLock("thread", 5000, 20);
                System.out.println("["+Thread.currentThread().getName()+"]获取锁 "+ order);
                if(order != null){
                    boolean order1 = distributedLock.releaseLock("thread", order, 5000);
                    System.out.println("["+Thread.currentThread().getName()+"]释放锁 "+order1);
                }
            },"thread_"+i).start();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
