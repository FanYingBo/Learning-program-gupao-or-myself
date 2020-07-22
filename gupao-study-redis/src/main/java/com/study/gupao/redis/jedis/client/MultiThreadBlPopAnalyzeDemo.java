package com.study.gupao.redis.jedis.client;


import com.study.gupao.redis.jedis.util.JedisClusterUtil;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 支持先进先出
 */
public class MultiThreadBlPopAnalyzeDemo {

    static int count = 10;
    static String key = "list:test";
    public static void main(String[] args) {
        PutThread putThread = new PutThread(count, key);
        putThread.start();
        TakeThread takeThread = new TakeThread(key);
        takeThread.start();

    }

}
class PutThread extends Thread{

    private int count;
    private String key;

    public PutThread(int count, String key){
        this.count = count;
        this.key = key;
    }
    @Override
    public void run() {
        int n = count;
        while(n > 0){
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            UUID uuid = UUID.randomUUID();
            System.out.println("放入数据："+uuid.toString());
            JedisClusterUtil.lPush(key,uuid.toString());
            n--;
        }
    }
}

class TakeThread extends Thread{
    private String key;

    public TakeThread(String key){
        this.key = key;
    }

    @Override
    public void run() {
        while(true){
            List<String> strings = JedisClusterUtil.bRPop(this.key);
            System.out.println("["+Thread.currentThread().getName()+"] 取出数据：" + strings);
        }
    }
}
