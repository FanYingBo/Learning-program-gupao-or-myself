package com.study.selfs.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 均匀分配批次
 */
public class NodeListBatchHandleDemo {



    public static void main(String[] args) throws IOException {
        List<Integer> nodeList = new ArrayList<>();
        nodeList.add(1);
        nodeList.add(2);
        nodeList.add(3);
        nodeList.add(4);
        nodeList.add(5);
        nodeList.add(6);
        BatchHandle batchHandle = new BatchHandle(nodeList);
        for(int num = 0;num < 10;num++){
            BatchThread batchThread = new BatchThread("thread-"+num,12,batchHandle);
            batchThread.start();
        }
    }
}

class BatchHandle{

    private AtomicInteger index = new AtomicInteger(0);

    private List<Integer> nodeList;


    public BatchHandle(List<Integer> nodeList) {
        this.nodeList = nodeList;
    }

    public void dealProcess(Integer anInt){
        Integer integer = 0;
        for (int i = 0; i < anInt; i++) {
            if (index.get() >= getNodesSize()) {
                index = new AtomicInteger(0);
            }
            integer = nodeList.get(index.get());
            index.getAndIncrement();
            System.out.println("thread: "+ Thread.currentThread().getName()+" "+integer);
        }
    }

    public Integer getNodesSize(){
        return nodeList != null ? nodeList.size() : 0;
    }
}

class BatchThread extends Thread{

    private int batchNum;

    private BatchHandle batchHandle;

    public BatchThread(String threadName, int batchNum, BatchHandle batchHandle){
        super(threadName);
        this.batchNum = batchNum;
        this.batchHandle = batchHandle;
    }
    @Override
    public void run(){
        batchHandle.dealProcess(batchNum);
    }

}