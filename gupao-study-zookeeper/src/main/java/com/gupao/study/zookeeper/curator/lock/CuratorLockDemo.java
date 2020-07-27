package com.gupao.study.zookeeper.curator.lock;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.RetryUntilElapsed;

import java.util.concurrent.TimeUnit;

public class CuratorLockDemo {

    public static void main(String[] args) throws InterruptedException {
        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder()
                .connectString("192.168.8.156:2181")
                .retryPolicy(new RetryUntilElapsed(5000,1000))
                .connectionTimeoutMs(3000)
                .build();
        curatorFramework.start();
        curatorFramework.blockUntilConnected();
        InterProcessMutex lock = new InterProcessMutex(curatorFramework,"/lock");
        for(int i = 0;i < 10;i++){
            new Thread(()->{
                try {
                    lock.acquire();
                    System.out.println("["+Thread.currentThread().getName()+"] 获取锁成功");
                    TimeUnit.SECONDS.sleep(5);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }finally {
                    try {
                        lock.release();
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }

            }).start();
        }
    }
}
