package com.gupao.study.zookeeper.curator.leaderselector;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.retry.RetryUntilElapsed;

public class LeaderSelectorDemo {

    public static void main(String[] args) {
        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder()
                .connectString("192.168.8.156:2181")
                .retryPolicy(new RetryUntilElapsed(5000,1000))
                .connectionTimeoutMs(3000)
                .build();
        curatorFramework.start();
        try {
            curatorFramework.blockUntilConnected();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LeaderSelector leaderSelector = new LeaderSelector(curatorFramework,"/leader",new CustomSelectorListener("client"));
        leaderSelector.start();
    }
}
