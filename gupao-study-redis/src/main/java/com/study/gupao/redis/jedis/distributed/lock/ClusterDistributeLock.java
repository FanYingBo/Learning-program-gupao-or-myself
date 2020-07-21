package com.study.gupao.redis.jedis.distributed.lock;

/**
 * redis 集群分布式所测试
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

    public static void testMultiThread() {
        int count = 2;
        for(int i = 0; i< count;i++){
            new Thread(()->{
                RedisClusterDistributedLock distributedLock = new RedisClusterDistributedLock();
                String order = distributedLock.acquireLock("thread", 5000, 20);
                System.out.println("["+Thread.currentThread().getName()+"]获取锁 "+ order);
                if(order != null){
                    boolean order1 = distributedLock.releaseLock("thread", order, 5000);
                    System.out.println("["+Thread.currentThread().getName()+"]释放锁 "+order1);
                }
            },"thread_"+i).start();
        }
    }

}
