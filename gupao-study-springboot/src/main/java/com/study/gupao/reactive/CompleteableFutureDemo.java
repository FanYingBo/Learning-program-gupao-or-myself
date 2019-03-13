package com.study.gupao.reactive;

import java.util.concurrent.CompletableFuture;

public class CompleteableFutureDemo {

    public static void main(String[] args) {
        print("主线程");
        CompletableFuture.supplyAsync(() -> {
            print("第一步返回 Hello");
            return "Hello";
        }).thenApplyAsync(s -> { //thenApply 串行  thenApplyAsync 并行
            print("第二步返回第一步结果+ Wold");
            return s+" Wold";
        }).thenAccept(CompleteableFutureDemo::print).join();// 并行+join

    }

    public static void print(String message){
        System.out.printf("[线程：%s] %s\n",Thread.currentThread().getName(),message);
    }
}
