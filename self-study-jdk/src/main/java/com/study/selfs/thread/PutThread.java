package com.study.selfs.thread;

import com.study.selfs.test.util.PrintUtils;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

public class PutThread extends Thread {

    private BlockingQueue<Integer> arrayBlockingQueue ;

    private CountDownLatch countDownLatch;

    private int count;

    public PutThread(BlockingQueue arrayBlockingQueue, CountDownLatch countDownLatch, int count){
        this.arrayBlockingQueue = arrayBlockingQueue;
        this.countDownLatch = countDownLatch;
        this.count = count;
    }

    @Override
    public void run() {
        countDownLatch.countDown();
        int exp;
        int all = 100;
        int mod = all / count;
        exp = all % count == 0 ? mod : mod +1;
        while(exp > 0){
            try {
                for(int num = 0; num < count;num++){
                    arrayBlockingQueue.put(--all);
//                    arrayBlockingQueue.offer(--all);//offer会出现元素丢失
//      1.  使用 put 避免死锁（ArrayBlockingQueue和LinkedBlockingQueue 已证明）
//
                    PrintUtils.print(this,"放入第 "+ (mod - (exp-1))+" 组数据："+all);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                exp--;
            }
        }

    }
}
