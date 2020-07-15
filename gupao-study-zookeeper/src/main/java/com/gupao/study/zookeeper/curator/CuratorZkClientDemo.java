package com.gupao.study.zookeeper.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryUntilElapsed;

public class CuratorZkClientDemo {

    public static void main(String[] args) {
        //
        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder()
                .connectString("192.168.8.156:2181")
                .retryPolicy(new RetryUntilElapsed(5, 3000))
                .connectionTimeoutMs(30000)
                .build();

        try {
            curatorFramework.start();
            curatorFramework.create().forPath("/curator","first".getBytes());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

}
