package com.gupao.study.zookeeper.distribute.lock;

import com.gupao.study.zookeeper.distribute.lock.curator.CuratorDistributeLockClientFactory;

import java.util.concurrent.TimeUnit;

public class DistributeLockTestDemo {

    static int THREAD_COUNT = 10;

    public static void main(String[] args) {
        CuratorDistributeLockClientFactory factory = new CuratorDistributeLockClientFactory();
        try{
            DistributeLockClient client = factory.createClient(20000, "192.168.8.156:2181", "/curator");
            client.start();
            for(int i = 0;i < THREAD_COUNT;i++){
                Integer integer = new Integer(i);
                Thread thread = new Thread(()->{
                    try {
                        TimeUnit.SECONDS.sleep(integer);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    String lockPath = client.acquireWithUnBlocked();
                    System.out.println("["+Thread.currentThread().getName()+"]获取锁："+lockPath);
                    client.releaseLock(lockPath);
                });
                thread.start();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
