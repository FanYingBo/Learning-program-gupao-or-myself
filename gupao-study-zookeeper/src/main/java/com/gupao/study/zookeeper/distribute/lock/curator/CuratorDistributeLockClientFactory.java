package com.gupao.study.zookeeper.distribute.lock.curator;

import com.gupao.study.zookeeper.distribute.lock.DistributeLockClient;
import com.gupao.study.zookeeper.distribute.lock.ZKDistributeLockClientFactory;

/**
 * 工厂方法模式
 */
public class CuratorDistributeLockClientFactory implements ZKDistributeLockClientFactory {
    @Override
    public DistributeLockClient createClient(int timeOut, String ipAddress, String path) {
        return new CuratorDistributeLock(timeOut,ipAddress, path);
    }
}
