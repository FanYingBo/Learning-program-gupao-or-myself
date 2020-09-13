package com.study.self.interview.difficulties.filequery;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.util.LinkedHashMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 40G / 512M = 80 连接文件查找连接 有内存限制 (有换行或者是www.*.com?)
 * 测试使用 125M/10M
 * 1. 内存
 * 2. 线程个数
 */
public class InternetLinkFileQuery {

    private AtomicReference<String> resultReference;

    private LinkedHashMap<Integer,FindThread> runnables;

    /**
     * 文件地址
     */
    private String filePath;

    private RandomAccessFile file;

    private long fileSize;

    private LongAdder totalSize;

    private long runSize;

    private volatile boolean stop;

    private String queryStr;

    private int threadCount;

    private long everyThreadMemory;

    private long lastEndPosition;

    private int threadId = 0;

    private int processors;

    private Lock lock = new ReentrantLock();

    public  InternetLinkFileQuery(String filePath,String queryStr){
        this.filePath = filePath;
        this.totalSize = new LongAdder();
        this.queryStr = queryStr;
    }

    public void init(){
        check();
        this.resultReference = new AtomicReference<>();
        this.runnables = new LinkedHashMap<>();
        this.processors = Runtime.getRuntime().availableProcessors();
        try {
            this.file = new RandomAccessFile(this.filePath,"rw");
            this.fileSize = file.getChannel().size();
            evaluate();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private FindThread cutFile(){
        threadId ++;
        FindThread findThread = new FindThread(threadId,this);
        findThread.setThreadStart(this.lastEndPosition);
        findThread.cutFile();
        this.lastEndPosition = findThread.getThreadEnd();
        return findThread;
    }


    private void evaluate() {
        if(this.threadCount > this.processors || this.threadCount == 0){
            setThreadCount(this.processors);
        }
        this.everyThreadMemory = (long)(Runtime.getRuntime().freeMemory() * 0.6 / this.threadCount) / 2;
        this.runSize = fileSize / everyThreadMemory +1;
    }

    private void check(){
        if(this.filePath == null || "".equals(this.filePath.trim())){
            throw new NullPointerException("file path is null");
        }
    }

    public FileChannel fileChannel(){
        return this.file.getChannel();
    }

    public long getEveryThreadMemory(){
        return this.everyThreadMemory;
    }

    public void setResult(){
        resultReference.set(this.queryStr);
    }

    public String getQueryStr(){
        return this.queryStr;
    }

    public void addCompletableSize(long runSize){
        totalSize.add(runSize);
    }
    public boolean isStop(){
        return stop;
    }

    public void stop(){
        stop = Boolean.TRUE;
    }

    public boolean isEndFile(){
        return fileSize <= lastEndPosition;
    }

    public void put(int threadId, FindThread findThread){
        try{
            lock.lock();
            this.runnables.put(threadId,findThread);
        }finally {
            lock.unlock();
        }

    }
    public  void remove(int id){
        try{
            lock.lock();
            this.runnables.remove(id);
        }finally {
            lock.unlock();
        }
    }

    public long getFileSize() {
        return fileSize;
    }

    public int getThreadCount() {
        return threadCount;
    }

    public void setThreadCount(int threadCount) {
        this.threadCount = threadCount;

    }

    public void waitAllThreadComplete(){
        while(!isStop()){
            if(runnables.size() < this.threadCount && !isEndFile()){
                FindThread runnable = cutFile();
                put(runnable.getRunId(),runnable);
                new Thread(runnable).start();
            }
        }
        if(resultReference.get() != null && queryStr.equals(resultReference.get())){
            System.out.println("找到结果，"+ resultReference.get());
            System.out.println("runSize: "+ runSize);
            System.out.println("threadCount: "+ threadCount);
            System.out.println("fileSize: "+ fileSize);
            System.out.println("totalSize: "+ totalSize.sum());
            System.out.println("lastPosition: "+lastEndPosition);
        }else{
            System.out.println("未找到结果");
        }
    }
}
