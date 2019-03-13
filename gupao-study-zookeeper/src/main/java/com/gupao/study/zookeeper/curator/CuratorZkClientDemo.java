package com.gupao.study.zookeeper.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;

public class CuratorZkClientDemo {

    public static void main(String[] args) {
        //
        CuratorFramework build = CuratorFrameworkFactory.builder()
                .connectString("")
                .connectionTimeoutMs(3000)
                .build();
    }

}
