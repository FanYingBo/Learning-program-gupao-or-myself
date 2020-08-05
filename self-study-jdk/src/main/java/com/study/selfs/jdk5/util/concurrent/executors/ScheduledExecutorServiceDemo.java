package com.study.selfs.jdk5.util.concurrent.executors;

import java.util.concurrent.*;

/**
 *  可以用於延時執行的一次性定時器任務
 */
public class ScheduledExecutorServiceDemo {

    private static final ScheduledExecutorService schedule = new ScheduledThreadPoolExecutor(2, new ThreadPoolExecutor.CallerRunsPolicy());

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        schedule.schedule(()->{
            System.out.println("execute delay " + (System.currentTimeMillis() - startTime)/1000);
        },2, TimeUnit.SECONDS);
        schedule.submit(()-> System.out.println("execute task"));
        schedule.shutdown();
    }
}
