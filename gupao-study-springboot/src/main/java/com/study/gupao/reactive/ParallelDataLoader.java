package com.study.gupao.reactive;


import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ParallelDataLoader extends DataLoader {
    @Override
    protected void doLoad() {

        ExecutorService executorService = Executors.newFixedThreadPool(3);// 创建线程池
        CompletionService completionService = new ExecutorCompletionService(executorService);
        completionService.submit(super::loadConfigurations, null);//  耗时 >= 1s
        completionService.submit(super::loadUsers, null);//  耗时 >= 2s
        completionService.submit(super::loadOrders, null);//  耗时 >= 3s
        int count = 0;
        while (count < 3) {// 等待三个任务完成
            if (completionService.poll() != null) {
                count++;
            }
        }
        executorService.shutdown();
    }

    public static void main(String[] args) {
        new ParallelDataLoader().doLoad();
    }
}
