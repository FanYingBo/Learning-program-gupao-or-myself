package com.gupao.study.zookeeper.distribute.lock.curator;

import com.gupao.study.zookeeper.distribute.lock.AbstractDistributeLockClient;
import com.gupao.study.zookeeper.distribute.lock.DistributeLockClient;
import com.gupao.study.zookeeper.distribute.lock.LockException;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.RetryUntilElapsed;
import org.apache.zookeeper.CreateMode;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * curator 创建分布式锁
 * 新构思：使用阻塞队列构建，听过监听来访问阻塞队列，获取锁
 */
public class CuratorDistributeLock extends AbstractDistributeLockClient implements DistributeLockClient {

    private CuratorFramework curatorFramework ;
    private ThreadLocal<String> threadLockPath = new ThreadLocal<>();
    private PathChildrenCache pathChildrenCache;
    private PathChildrenCacheListener listener;
    private ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private String totalPath;
    public CuratorDistributeLock(int timeOut,String ipAddress,String lockPath) {
        super(ipAddress, lockPath);
        totalPath = this.path +"/";
        curatorFramework = CuratorFrameworkFactory.builder()
                .connectString(ipAddress)
                .connectionTimeoutMs(timeOut)
                .retryPolicy(new RetryUntilElapsed(300000,3000))
                .build();
        pathChildrenCache = new PathChildrenCache(curatorFramework, lockPath, Boolean.TRUE);
//        listener = (client,event) -> {
//            if(event.getType() == PathChildrenCacheEvent.Type.CHILD_REMOVED
//                    || event.getType() == PathChildrenCacheEvent.Type.CHILD_ADDED){
//                List<String> strings = client.getChildren().forPath(path);
//                System.out.println("child changed: "+event.getType() +" childs: "+strings);
//                Collections.sort(strings);
//                childPaths.set(strings);
//            }
//        };
//        pathChildrenCache.getListenable().addListener(listener,executorService);
//        try {
//            pathChildrenCache.start();
//        } catch (Exception exception) {
//            exception.printStackTrace();
//        }
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
            String createdPath = curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath(totalPath);
            List<String> sortedChildPath = getSortedChildPath();
            if(sortedChildPath != null && !sortedChildPath.isEmpty()){
                if(createdPath.endsWith(sortedChildPath.get(0))){
                    threadLockPath.set(createdPath);
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
    public String acquireWithBlocked(int times, int delayInterval, int timeOut, TimeUnit timeUnit) {
        if(timeOut < delayInterval){
            return null;
        }
        int cacheTimes = times;
        long startTime = System.currentTimeMillis();
        long loopTime = startTime;
        long delayMillis = timeUnit.toMillis(delayInterval);
        long timeOutMillis = startTime + timeUnit.toMillis(timeOut);
        while(cacheTimes > 0){
            try {
                if(System.currentTimeMillis() - loopTime < delayMillis){
                    continue;
                }
                long escapeTime = System.currentTimeMillis() - startTime;
                if(escapeTime > timeOutMillis){
                    return null;
                }
                String createdPath = curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath(totalPath);
                List<String> sortedChildPath = getSortedChildPath();
                if(createdPath.endsWith(sortedChildPath.get(0))){
                    threadLockPath.set(createdPath);
                    return createdPath;
                }else{
                    // 若不是最小的则删除
                    curatorFramework.delete().forPath(createdPath);
                }
                loopTime = System.currentTimeMillis();
                cacheTimes --;
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
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

    @Override
    public boolean releaseLock() {
        String lockPath = null;
        if((lockPath = threadLockPath.get()) != null){
            try {
                curatorFramework.delete().forPath(lockPath);
                threadLockPath.remove();
                return Boolean.TRUE;
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        return Boolean.FALSE;
    }


    private List<String> getSortedChildPath(){
        List<String> strings = null;
        try {
            strings = curatorFramework.getChildren().forPath(path);
            Collections.sort(strings);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return strings;
    }

}
