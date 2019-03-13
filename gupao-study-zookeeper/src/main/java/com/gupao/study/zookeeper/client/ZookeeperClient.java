package com.gupao.study.zookeeper.client;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.data.Stat;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @see CreateMode
 * @see ZooKeeper
 * @see ZooDefs.Ids
 * @see ACL
 * @see KeeperException.Code
 *
 */
public class ZookeeperClient {

    private ZooKeeper zooKeeper;

    private Watcher watcher;

    private Stat stat;

    private long sessionId;

    public ZookeeperClient(ZooKeeper zooKeeper, Watcher watcher){
        this(zooKeeper,watcher,null);
    }


    public ZookeeperClient(ZooKeeper zooKeeper, Watcher watcher, Stat stat) {
        this.zooKeeper = zooKeeper;
        this.watcher = watcher;
        this.stat = stat;
        this.sessionId = zooKeeper.getSessionId();
    }

    public ZookeeperClient(String zkUrl, int timeout, boolean watch){
        this.watcher = new ZookeeperWatcherInst(this);
        try {
            if (watch) {
                this.zooKeeper = new ZooKeeper(zkUrl, timeout, this.watcher);
            } else {
                this.zooKeeper = new ZooKeeper(zkUrl, timeout, null);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建持久节点
     * @param path
     * @param value
     * @param id
     * @return
     */
    public boolean createPersistentPath(String path, String value, ArrayList<ACL> id ){
        if (!exitPath(path, false, true)) {
            StringCallBackImpl stringCallBack = new StringCallBackImpl();
            return createPath(path, value.getBytes(), id, CreateMode.PERSISTENT, stringCallBack, "context");
        } else {
            StatCallBackImpl statCallBack = new StatCallBackImpl();
            return setData(path, value.getBytes(), statCallBack, "set pathdata context");
        }
    }

    /**
     * 创建持久子节点
     * @param parentPath
     * @param childPath
     * @param id
     * @return
     */
    public boolean createPersistentChildPath(String parentPath,String childPath,String value,ArrayList<ACL> id){
        if (exitPath(parentPath, false, true)) {
            String fullChildPath = parentPath + childPath;
            if (!exitPath(fullChildPath, true, true)) {
                StringCallBackImpl stringCallBack = new StringCallBackImpl();
                return createPath(fullChildPath, value.getBytes(), id, CreateMode.PERSISTENT, stringCallBack, "create child path");
            } else {
                StatCallBackImpl statCallBack = new StatCallBackImpl();
                return setData(fullChildPath, value.getBytes(), statCallBack, "set child data");
            }
        } else {
            return false;
        }
    }


    /**
     * 创建子持久节点，若父节点不存在则先创建父节点
     * @see CreateMode#PERSISTENT
     * @param parentPath
     * @param childPath
     * @param value
     * @param id
     * @return
     */
    public boolean createPersistentChildPathIfNeedParentPath(String parentPath,String childPath,String value, ArrayList<ACL> id){
        StringCallBackImpl stringCallBack = new StringCallBackImpl();
        String fullChildPath = parentPath + childPath;
        if (exitPath(parentPath, false, true)) {
            return createPath(fullChildPath, value.getBytes(), id, CreateMode.PERSISTENT, stringCallBack, "create children context");
        } else {
            boolean create_parent = createPath(parentPath, value.getBytes(), id, CreateMode.PERSISTENT, stringCallBack, "create parent context");
            if (create_parent) {
                if (exitPath(parentPath, false, true)) {
                    if (!exitPath(fullChildPath, true, true)) {
                        return createPath(fullChildPath, value.getBytes(), id, CreateMode.PERSISTENT, stringCallBack, "create parent context");
                    } else {
                        StatCallBackImpl statCallBack = new StatCallBackImpl();
                        return setData(fullChildPath, value.getBytes(), statCallBack, "set child path data context");
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
    }


    public boolean createEphemeralPath(String path,String value, ArrayList<ACL> id) {
        if (!exitPath(path, false, true)) {
            StringCallBackImpl stringCallBack = new StringCallBackImpl();
            return createPath(path, value.getBytes(), id, CreateMode.EPHEMERAL, stringCallBack, "context");
        } else {
            StatCallBackImpl statCallBack = new StatCallBackImpl();
            return setData(path, value.getBytes(), statCallBack, "set pathdata context");
        }
    }

    /**
     * 创建临时子节点
     * @param parentPath
     * @param childPath
     * @param id
     * @return
     */
    public boolean createEphemeralChildPath(String parentPath,String childPath,String value,ArrayList<ACL> id){
        if (exitPath(parentPath, false, true)) {
            String fullChildPath = parentPath + childPath;
            if (!exitPath(fullChildPath, true, true)) {
                StringCallBackImpl stringCallBack = new StringCallBackImpl();
                return createPath(fullChildPath, value.getBytes(), id, CreateMode.EPHEMERAL, stringCallBack, "create child path");
            } else {
                StatCallBackImpl statCallBack = new StatCallBackImpl();
                return setData(fullChildPath, value.getBytes(), statCallBack, "set ephemeral child data");
            }
        } else {
            return false;
        }
    }


    /**
     * 创建子持久节点，若父节点不存在则先创建父节点
     * @see CreateMode#PERSISTENT
     * @param parentPath
     * @param childPath
     * @param value
     * @param id
     * @return
     */
    public boolean createEphemeralChildPathIfNeedParentPath(String parentPath,String childPath,String value, ArrayList<ACL> id){
        StringCallBackImpl stringCallBack = new StringCallBackImpl();
        String fullChildPath = parentPath + childPath;
        if (exitPath(parentPath, false, true)) {
            return createPath(fullChildPath, value.getBytes(), id, CreateMode.EPHEMERAL, stringCallBack, "create ephemeral parent path children context");
        } else {
            boolean create_parent = createPath(parentPath, value.getBytes(), id, CreateMode.EPHEMERAL, stringCallBack, "create ephemeral parent path context");
            if (create_parent) {
                if (exitPath(parentPath, false, true)) {
                    if (!exitPath(fullChildPath, true, true)) {
                        return createPath(fullChildPath, value.getBytes(), id, CreateMode.EPHEMERAL, stringCallBack, "create ephemeral parent path context");
                    } else {
                        StatCallBackImpl statCallBack = new StatCallBackImpl();
                        return setData(fullChildPath, value.getBytes(), statCallBack, "set ephemeral child path data context");
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
    }


    /**
     *
     * @param path
     * @return
     */
    public List<String> getChildPaths(String path) {
        ChildCallBackImpl childCallBack = new ChildCallBackImpl();
        this.zooKeeper.getChildren(path,new ZookeeperWatcherInst(this),childCallBack,"get children paths context");
        lockCallBack(childCallBack);
        return childCallBack.childrens;
    }

    /**
     *
     * @param path
     * @param value
     * @param asyncCallback
     * @param ctx
     * @return
     */
    private boolean setData(String path, byte[] value,  StatCallBackImpl asyncCallback, Object ctx){
        zooKeeper.setData(path, value, this.stat.getVersion(),asyncCallback,ctx);
        lockCallBack(asyncCallback);
        System.out.println(asyncCallback);
        this.stat = asyncCallback.stat;
        return asyncCallback.resultCode == KeeperException.Code.OK.intValue();
    }
    /**
     *
     * @param path
     * @param value
     * @param id
     * @param createMode
     * @param asyncCallback
     * @param ctx
     * @return
     */
    private boolean createPath(String path, byte[] value, ArrayList<ACL> id , CreateMode createMode, StringCallBackImpl asyncCallback, Object ctx){
        zooKeeper.create(path, value, id,createMode, asyncCallback, ctx);
        lockCallBack(asyncCallback);
        System.out.println(asyncCallback);
        return asyncCallback.resultCode == KeeperException.Code.OK.intValue();
    }
    /**
     * 获取数据
     * @param path
     * @param watch
     * @return
     */
    public String getData(String path, boolean watch){
        String dataStr = null;
        try {
            byte[] data = this.zooKeeper.getData(path, watch, this.stat);
            dataStr = new String(data);
        } catch (KeeperException e) {
            return null;
        } catch (InterruptedException e) {
            return null;
        }
        return dataStr;

    }

    /**
     * 删除path
     * @param path
     * @return
     */
    public String deletePath(String path){
        String data = null;
        boolean ischild = path.substring(1).indexOf("/") < 0;
        if(exitPath(path,ischild,true)){
            try {
                data = this.getData(path,true);
                this.zooKeeper.delete(path,this.stat.getVersion());
            } catch (InterruptedException e) {
                return null;
            } catch (KeeperException e) {
                return null;
            }
        }
        return  data;

    }
    /**
     * 判断节点是否存在
     * @param path
     * @param watch
     * @return
     */
    public boolean exitPath(String path,boolean ischild, boolean watch){
        StatCallBackImpl statCallBack = new StatCallBackImpl();
        if (watch) {
            if (ischild) {
                zooKeeper.exists(path, new ZookeeperWatcherInst(this), statCallBack, "exists child path context");
            } else {
                zooKeeper.exists(path, this.watcher, statCallBack, "exists child path context");
            }
        }
        lockCallBack(statCallBack);
        this.stat = statCallBack.stat;
        if(this.stat == null){
            return false;
        }
        if(statCallBack.resultCode == 0 && this.stat.getDataLength() > 0 ){
            return true;
        }else{
            return false;
        }
    }

    private void lockCallBack(AsyncCallback asyncCallback){
        while(true){
            if(asyncCallback instanceof CallBack && ((CallBack) asyncCallback).context != null){
                break;
            }
        }
    }


    /**
     * 关闭
     */
    public void close(){
        try {
            this.zooKeeper.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private abstract class CallBack{

        protected Object context;

        protected int resultCode;

        protected String path;

        @Override
        public String toString() {
            return "CallBack{" +
                    "context=" + context +
                    ", resultCode=" + resultCode +
                    ", path='" + path + '\'' +
                    '}';
        }
    }
    /***
     * 异步回调
     * @see KeeperException.Code
     * resultCode 0:调用成功
     *            -4：客户端与服务端已经断开
     *            -110：指定节点已经存在
     *
     */
    class StringCallBackImpl  extends CallBack implements AsyncCallback.StringCallback {
        @Override
        public void processResult(int rc, String path, Object ctx, String name) {
            this.context = ctx;
            this.resultCode = rc;
            this.path = path;
        }

    }

    /**
     * 回调
     */
   class StatCallBackImpl extends CallBack implements AsyncCallback.StatCallback {

        private Stat stat;

        @Override
        public void processResult(int rc, String path, Object ctx, Stat stat) {
            this.context = ctx;
            this.resultCode = rc;
            this.path = path;
            this.stat = stat;
        }
    }

    class ChildCallBackImpl extends CallBack implements AsyncCallback.ChildrenCallback{

        private List<String> childrens;
        @Override
        public void processResult(int rc, String path, Object ctx, List<String> children) {
            this.context = ctx;
            this.resultCode = rc;
            this.path = path;
            this.childrens = children;
        }
    }

}
