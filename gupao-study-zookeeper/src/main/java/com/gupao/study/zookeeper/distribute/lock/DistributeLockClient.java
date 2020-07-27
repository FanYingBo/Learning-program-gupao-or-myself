package com.gupao.study.zookeeper.distribute.lock;

import java.util.concurrent.TimeUnit;

public interface DistributeLockClient {

    void start();

    /**
     * 通过有序节点来设置锁，有序节点的最小值表示已经获取到锁
     * @return
     */
    String acquireWithUnBlocked() throws LockException;

    /**
     * @param times 重试次数
     * @param delayInterval 延时，周期
     * @param timeOut 超时时间
     * @return
     */
    String acquireWithBlocked(int times, int delayInterval, int timeOut, TimeUnit timeUnit) throws LockException;

    /**
     * 释放锁
     * @param path
     * @return
     */
    boolean releaseLock(String path);
    /**
     * 释放锁
     * @param path
     * @return
     */
    boolean releaseLock();
}
