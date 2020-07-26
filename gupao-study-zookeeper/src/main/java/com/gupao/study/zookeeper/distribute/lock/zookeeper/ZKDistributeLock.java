package com.gupao.study.zookeeper.distribute.lock.zookeeper;

import com.gupao.study.zookeeper.distribute.lock.AbstractDistributeLockClient;
import com.gupao.study.zookeeper.distribute.lock.DistributeLockClient;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

/**
 * zookeeper 客户端创建分布式锁
 */
public class ZKDistributeLock extends AbstractDistributeLockClient implements DistributeLockClient {

    private ZooKeeper zkClient;

    protected ZKDistributeLock(int timeOut,String ipAddresses,String path) {
        super(ipAddresses, path);
        try {
            this.zkClient = new ZooKeeper(ipAddresses,timeOut,null,false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doStart() {

    }

    @Override
    public String acquireWithUnBlocked() {

        return null;
    }

    @Override
    public String acquireWithBlocked(int times, int delayInterval, int timeOut) {

        return null;
    }

    @Override
    public boolean releaseLock(String path) {
        return false;
    }
}
