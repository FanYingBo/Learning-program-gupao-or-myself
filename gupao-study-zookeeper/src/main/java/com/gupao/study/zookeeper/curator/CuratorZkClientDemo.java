package com.gupao.study.zookeeper.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.retry.RetryUntilElapsed;
import org.apache.zookeeper.CreateMode;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CuratorZkClientDemo {

    private static String path = "/curator";

    public static void main(String[] args) {
        CuratorZkClientDemo zkClientDemo = new CuratorZkClientDemo();

        zkClientDemo.init();

//        zkClientDemo.forPathWithListener(path);
        zkClientDemo.forPathChildrenWithListener(path,"");
        zkClientDemo.close();
    }

    private CuratorFramework curatorFramework;
    private ExecutorService listenerExecutor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public void init(){
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
    public void forPathWithListener(String path){
        try {
            // 如果路径存在则调用setData
            NodeCache nodeCache = new NodeCache(curatorFramework, path);
            nodeCache.getListenable().addListener(()-> {
                    System.out.println("["+Thread.currentThread().getName()+"]"+"path: "+nodeCache.getPath());
                    System.out.println("["+Thread.currentThread().getName()+"]"+"child : "+nodeCache.getCurrentData());
                    System.out.println("["+Thread.currentThread().getName()+"]"+"child data: "+new String(nodeCache.getCurrentData().getData()));
            }, listenerExecutor);
            nodeCache.start(true);
            curatorFramework.create().orSetData().forPath(path,"second".getBytes());
            curatorFramework.setData().forPath(path,"third".getBytes());
            curatorFramework.delete().forPath(path);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void forPathChildrenWithListener(String parentPath , String childPath){
        try {
            PathChildrenCache pathChildrenCache = new PathChildrenCache(curatorFramework,parentPath,Boolean.TRUE,Boolean.FALSE,listenerExecutor);
            pathChildrenCache.getListenable().addListener((client, event) ->  {
                    System.out.println("event type: "+event.getType());
                    System.out.println("child data: "+event.getData());
            });
            pathChildrenCache.start(PathChildrenCache.StartMode.NORMAL);
            String totalPath = parentPath + "/"+ childPath;
            curatorFramework.create().withMode(CreateMode.PERSISTENT_SEQUENTIAL).forPath(totalPath);
            List<String> strings = curatorFramework.getChildren().forPath(parentPath);
            System.out.println(strings);
//            curatorFramework.delete().forPath(totalPath);
//            pathChildrenCache.close();
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

    public void close(){
        curatorFramework.close();
    }

}
