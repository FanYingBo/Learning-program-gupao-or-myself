package com.gupao.study.zookeeper.distribute.lock;

import com.google.common.base.Stopwatch;
import com.gupao.study.zookeeper.distribute.lock.curator.CuratorDistributeLockClientFactory;

import java.util.concurrent.TimeUnit;

public class DistributeLockTestDemo {

    static int THREAD_COUNT = 20;

    public static void main(String[] args) {
        CuratorDistributeLockClientFactory factory = new CuratorDistributeLockClientFactory();
        try{
            DistributeLockClient client = factory.createClient(20000, "192.168.8.156:2181", "/curator");
            client.start();
            for(int i = 0;i < THREAD_COUNT;i++){
                Thread thread = new Thread(()->{
                    Stopwatch started = Stopwatch.createStarted();
                    String lockPath = client.acquireWithBlocked(10,1,10,TimeUnit.SECONDS);
                    System.out.println("["+Thread.currentThread().getName()+"]获取锁："+lockPath+" 耗时："+started.elapsed(TimeUnit.MILLISECONDS));
                    client.releaseLock();
                    System.out.println("["+Thread.currentThread().getName()+"]释放锁："+lockPath);
                });
                thread.start();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
