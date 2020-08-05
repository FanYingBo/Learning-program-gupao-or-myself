package com.study.selfs.jdk5.util.concurrent.completionservice;

import java.util.concurrent.*;

/**
 * 并行计算
 * {@link CompletionService}
 */
public class CompletionServiceDemo {

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(3);
        CompletionService completionService = new ExecutorCompletionService(executorService);
        completionService.submit(()->loadMock("测试1", 1),null);
        completionService.submit(()->loadMock("测试2", 2),null);
        completionService.submit(()->loadMock("测试3", 3),null);
        int count = 3;
        while(count > 3){
            if(completionService.poll() != null){
                count++;
            }
        }
        executorService.shutdown();
    }

    private static void loadMock(String desc, long secondTime){
        long start = System.currentTimeMillis();
        long millisSeconds = TimeUnit.SECONDS.toMillis(secondTime);
        try {
            Thread.sleep(millisSeconds);
            long costTime = System.currentTimeMillis()-start;
            System.out.printf("[线程 : %s] %s 耗时 :  %d 毫秒\n", Thread.currentThread().getName(), desc, costTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
