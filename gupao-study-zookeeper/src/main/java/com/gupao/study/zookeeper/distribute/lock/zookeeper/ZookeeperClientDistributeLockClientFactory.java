package com.gupao.study.zookeeper.distribute.lock.zookeeper;

import com.gupao.study.zookeeper.distribute.lock.DistributeLockClient;
import com.gupao.study.zookeeper.distribute.lock.ZKDistributeLockClientFactory;

public class ZookeeperClientDistributeLockClientFactory implements ZKDistributeLockClientFactory {
    @Override
    public DistributeLockClient createClient(int timeOut, String ipAddress, String path) {
        return new ZKDistributeLock(timeOut,ipAddress,path);
    }
}
