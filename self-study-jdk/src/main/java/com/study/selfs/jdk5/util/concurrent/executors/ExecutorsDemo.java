package com.study.selfs.jdk5.util.concurrent.executors;


import com.study.selfs.jdk5.util.concurrent.callable.CallableDemo;

import java.util.List;
import java.util.concurrent.*;

/**
 *
 * @see java.util.concurrent.ExecutorService
 */
public class ExecutorsDemo {

    public static void main(String[] args) {
//        scheduleThreadPool();
//        fixedThreadPool();
        lambdaRetCall();
    }

    private static void lambdaRetCall(){
//        Executors.newCachedThreadPool();
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        Future<String> submit = executorService.submit(() -> {
            return "sdas";
        });
        executorService.shutdown();
        try {
            System.out.println(submit.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


    private static void fixedThreadPool(){
        ExecutorService executorService = Executors.newFixedThreadPool(8);
        CallableDemo callableDemo = new CallableDemo();
        List<CallableDemo> callableList = new CopyOnWriteArrayList<>();
        callableList.add(callableDemo);
        callableList.add(callableDemo);
        callableList.add(callableDemo);
        callableList.add(callableDemo);
        callableList.add(callableDemo);
        callableList.add(callableDemo);
        callableList.add(callableDemo);
        callableList.add(callableDemo);
        try {
            List<Future<String>> futures = executorService.invokeAll(callableList);
            futures.forEach(future -> {
                try {
                    System.out.println(future.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            });
            executorService.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private static void scheduleThreadPool(){
        ExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

        Future<Integer> submit = scheduledExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("[线程] " + Thread.currentThread().getName());
            }
        }, 2333);
        try {
            System.out.println(submit.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        scheduledExecutorService.shutdown();
    }



}

