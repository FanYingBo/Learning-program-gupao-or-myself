package com.gupao.study.zookeeper.client;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import java.io.File;

public class ZookeeperWatcherInst implements Watcher {

    private ZookeeperClient zookeeperClient;

    public ZookeeperWatcherInst(ZookeeperClient zookeeperClient) {
        this.zookeeperClient = zookeeperClient;
    }

    @Override
    public void process(WatchedEvent event) {
        System.out.println("path:"+event.getPath());
        System.out.println("state:"+event.getState());
        System.out.println("type:"+event.getType()+"\n");
        zookeeperClient.exitPath(event.getPath(),event.getPath().substring(1).indexOf("/") < 0,true);
    }
}
