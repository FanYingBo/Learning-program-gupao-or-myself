package com.gupao.study.zookeeper.distribute.lock;

public abstract class AbstractDistributeLockClient implements DistributeLockClient{

    protected String ipAddress;
    protected String path;

    protected AbstractDistributeLockClient(String ipAddress, String path) {
        this.ipAddress = ipAddress;
        this.path = path;
    }

    public void start(){
        doStart();
    }

    protected abstract void doStart();
}
