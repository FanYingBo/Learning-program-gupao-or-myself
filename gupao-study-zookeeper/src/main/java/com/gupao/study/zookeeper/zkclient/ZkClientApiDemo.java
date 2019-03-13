package com.gupao.study.zookeeper.zkclient;

import org.I0Itec.zkclient.ZkClient;

public class ZkClientApiDemo {

    public static void main(String[] args) {
        ZkClient zkClient = new ZkClient("127.0.0.1:2181",3000);
        zkClient.createEphemeral("/tmp");
    }
}
