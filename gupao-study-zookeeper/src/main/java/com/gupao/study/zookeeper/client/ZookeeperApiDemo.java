package com.gupao.study.zookeeper.client;

import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;


/**
 *
 * @see ZooKeeper
 * @see ZookeeperClient
 */
public class ZookeeperApiDemo {

    public static void main(String[] args) throws InterruptedException {
        String path = "/zkclient";
        String childPath = "/zkchild";

        ZookeeperClient zookeeperClient = new ZookeeperClient("192.168.8.156:2181",10000,true);
        zookeeperClient.createPersistentPath(path,"w2122",ZooDefs.Ids.OPEN_ACL_UNSAFE);
        zookeeperClient.createPersistentChildPath(path,childPath,"dddd",ZooDefs.Ids.OPEN_ACL_UNSAFE);
//        zookeeperClient.deletePath(path+childPath);


//        ZooKeeper zooKeeper = null;
//        try {
//            zooKeeper = new ZooKeeper("192.168.1.156:2181", 3000, new Watcher() {
//                @Override
//                public void process(WatchedEvent event) {
//                    System.out.println("type: "+event.getType()+" path: "+event.getPath()+" ");
//                }
//            });
//            System.out.println(zooKeeper.getState());
//            Stat stat = zooKeeper.exists("/zkclient3", false);
//            System.out.println(stat);
//            if (stat == null) {
//                zooKeeper.create("/zkclient3", "122".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT, new AsyncCallback.StringCallback() {
//                    @Override
//                    public void processResult(int rc, String path, Object ctx, String name) {
//                        System.out.println("ctx "+ctx+" rc "+rc+" path "+path);
//                    }
//                },"create path context");
//            }
//            byte[] data = zooKeeper.getData("/zkclient", zookeeperWatcherInst, stat);
//            System.out.println("data:" + new String(data) + " ver:" + stat.getVersion());
//            stat = zooKeeper.setData("/zkclient", "134".getBytes(), stat.getVersion());
//            System.out.println("modify ver:" + stat.getVersion());
//            zooKeeper.create("/zkclient/zkchild", "122".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
//            data = zooKeeper.getData("/zkclient/zkchild", zookeeperWatcherInst, stat);
//            System.out.println("data:" + new String(data) + " ver:" + stat.getVersion());
//            stat = zooKeeper.setData("/zkclient/zkchild", "212".getBytes(), stat.getVersion());
//            System.out.println("ver:" + stat.getVersion());
//            List<String> children = zooKeeper.getChildren("/", zookeeperWatcherInst);
//            System.out.println("childpath:" + children);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (KeeperException e) {
//            e.printStackTrace();
//        } finally {
//            if (zooKeeper != null) {
//                zooKeeper.close();
//            }
//        }
    }

}
