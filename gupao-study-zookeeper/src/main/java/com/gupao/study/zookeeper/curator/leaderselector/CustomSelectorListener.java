package com.gupao.study.zookeeper.curator.leaderselector;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListener;
import org.apache.curator.framework.state.ConnectionState;

import java.util.concurrent.CountDownLatch;

public class CustomSelectorListener implements LeaderSelectorListener {

    private String clientName;
    private CountDownLatch countDownLatch = new CountDownLatch(1);

    public CustomSelectorListener(String clientName) {
        this.clientName = clientName;
    }


    @Override
    public void takeLeadership(CuratorFramework client) throws Exception {
        System.out.println(clientName + " 成为了leader");
        // 这里通知其他的节点
        countDownLatch.await();
    }

    @Override
    public void stateChanged(CuratorFramework client, ConnectionState newState) {
        System.out.println("connect state change: "+ newState.name());
    }
}
