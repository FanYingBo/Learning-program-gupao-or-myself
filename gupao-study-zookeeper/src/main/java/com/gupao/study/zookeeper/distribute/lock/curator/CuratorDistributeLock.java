package com.gupao.study.zookeeper.distribute.lock.curator;

import com.gupao.study.zookeeper.distribute.lock.AbstractDistributeLockClient;
import com.gupao.study.zookeeper.distribute.lock.DistributeLockClient;
import com.gupao.study.zookeeper.distribute.lock.LockException;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryUntilElapsed;
import org.apache.zookeeper.CreateMode;

import java.util.Collections;
import java.util.List;

/**
 * curator 创建分布式锁
 * 新构思：使用阻塞队列构建，听过监听来访问阻塞队列，获取锁
 */
public class CuratorDistributeLock extends AbstractDistributeLockClient implements DistributeLockClient {

    private CuratorFramework curatorFramework ;

    public CuratorDistributeLock(int timeOut,String ipAddress,String lockPath) {
        super(ipAddress, lockPath);
        curatorFramework = CuratorFrameworkFactory.builder()
                .connectString(ipAddress)
                .connectionTimeoutMs(timeOut)
                .retryPolicy(new RetryUntilElapsed(300000,3000))
                .build();
    }

    @Override
    protected void doStart() {
        curatorFramework.start();
        try {
            curatorFramework.blockUntilConnected();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String acquireWithUnBlocked() {
        // 创建临时节点
        try {
            String totalPath = path + "/";
            String createdPath = curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath(totalPath);
            List<String> strings = curatorFramework.getChildren().forPath(path);
            Collections.sort(strings);
            if(!strings.isEmpty()){
                if(createdPath.endsWith(strings.get(0))){
                    return createdPath;
                }else{
                    // 若不是最小的则删除
                    curatorFramework.delete().forPath(createdPath);
                    return null;
                }
            }
        } catch (Exception exception) {
            throw new LockException("curator get path for locking failed",exception);
        }
        return null;
    }

    @Override
    public String acquireWithBlocked(int times, int delayInterval, int timeOut) {
        return null;
    }

    @Override
    public boolean releaseLock(String path) {
        try{
            if(path == null || "".equals(path)){
                return Boolean.FALSE;
            }
            curatorFramework.delete().forPath(path);
            return Boolean.TRUE;
        }catch (Exception e){
            return Boolean.FALSE;
        }
    }
}
