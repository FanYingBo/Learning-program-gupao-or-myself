package com.gupao.study.zookeeper.distribute.lock;

public interface ZKDistributeLockClientFactory {

    int DEFAULT_TIMEOUT = 3000;

    DistributeLockClient createClient(int timeOut,String ipAddress, String path);

}
