package com.study.self.interview.difficulties;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 40G连接文件查找连接 有内存限制 (有换行或者是www.*.com?)
 * 1. 内存
 * 2. 线程个数
 */
public class InternetLinkFileQuery {

    private AtomicReference<String> resultReference;

    private LinkedList<Thread> threadList;
    /**
     * 文件地址
     */
    private String filePath;
    /**
     * 内存空间大小
     */
    private long max_memory;

    private RandomAccessFile file;

    private long fileSize;

    private long currentPosition;

    private String[] currentCacheData;

    private boolean isFound;

    private long totalPosition;

    private String lastLine;

    private AtomicLong position;

    private int threadCount;
    private long everyThreadMemory;

    public InternetLinkFileQuery(String filePath){
        this.filePath = filePath;
        init();
    }

    public void init(){
        check();
        evaluate();
        this.currentPosition = 0;
        this.resultReference = new AtomicReference<>();
        this.threadList = new LinkedList<>();
        try {
            this.file = new RandomAccessFile(this.filePath,"rw");
            this.fileSize = file.length();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void evaluate() {
        this.threadCount = Runtime.getRuntime().availableProcessors();
        this.everyThreadMemory = (long)(Runtime.getRuntime().freeMemory() * 0.6 / this.threadCount);
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

    public void addThread(Thread thread){
        this.threadList.add(thread);
    }

}
