package com.study.selfs.thread;

import com.study.selfs.test.util.PrintUtils;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

public class TakeThread extends Thread{
    private BlockingQueue<Integer> arrayBlockingQueue ;

    private int count;

    private CountDownLatch countDownLatch;

    public TakeThread(BlockingQueue arrayBlockingQueue,CountDownLatch countDownLatch, int count ){
        this.arrayBlockingQueue = arrayBlockingQueue;
        this.countDownLatch = countDownLatch;
        this.count = count;
    }
    @Override
    public void run() {
        countDownLatch.countDown();
        int all = 100;
        int mod = all / count;
        int exp = all % count == 0 ? mod : ++mod;
        while(exp > 0){
            try {
                for(int num = 0; num < count;num++){
                    Integer take = arrayBlockingQueue.take(); //如果队列无值线程会阻塞
//                    Integer take = arrayBlockingQueue.poll();//如果队列无值会取出null
                    PrintUtils.print(this,"第"+(mod - (exp-1))+"次取出数据："+take);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                exp--;
            }
        }



    }
}
