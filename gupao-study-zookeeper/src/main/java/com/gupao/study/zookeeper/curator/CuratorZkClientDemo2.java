package com.gupao.study.zookeeper.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.retry.RetryUntilElapsed;
import org.apache.zookeeper.CreateMode;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.apache.curator.framework.recipes.cache.PathChildrenCache.StartMode.BUILD_INITIAL_CACHE;

public class CuratorZkClientDemo2 {

    private static String path = "/curator";

    public static void main(String[] args) {
        CuratorZkClientDemo2 zkClientDemo = new CuratorZkClientDemo2();

        zkClientDemo.init();

//        zkClientDemo.forPathWithListener(path);
        zkClientDemo.forPathChildrenWithListener(path + "/1");
        zkClientDemo.close();
    }

    private CuratorFramework curatorFramework;
    private ExecutorService listenerExecutor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public void init() {
        curatorFramework = CuratorFrameworkFactory.builder()
                .connectString("192.168.8.156:2181")
                .retryPolicy(new RetryUntilElapsed(5, 3000))
                .connectionTimeoutMs(30000)
                .build();
        curatorFramework.start();
        try {
            curatorFramework.blockUntilConnected();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void forPathWithListener(String path) {
        try {
            // 如果路径存在则调用setData
            NodeCache nodeCache = new NodeCache(curatorFramework, path);
            nodeCache.getListenable().addListener(() -> {
                System.out.println("[" + Thread.currentThread().getName() + "]" + "path: " + nodeCache.getPath());
                System.out.println("[" + Thread.currentThread().getName() + "]" + "child : " + nodeCache.getCurrentData());
                System.out.println("[" + Thread.currentThread().getName() + "]" + "child data: " + new String(nodeCache.getCurrentData().getData()));
            }, listenerExecutor);
            nodeCache.start(true);
            curatorFramework.create().orSetData().forPath(path, "second".getBytes());
            curatorFramework.setData().forPath(path, "third".getBytes());
            curatorFramework.delete().forPath(path);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void forPathChildrenWithListener(String path) {
        try {
            PathChildrenCache pathChildrenCache = new PathChildrenCache(curatorFramework, path, Boolean.TRUE, Boolean.FALSE, listenerExecutor);
            pathChildrenCache.getListenable().addListener((client, event) -> {
                System.out.println("event type: " + event.getType());
                System.out.println("child data: " + event.getData());
            });
            pathChildrenCache.start(BUILD_INITIAL_CACHE);
            curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath(path);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void close() {
        curatorFramework.close();
    }
}
