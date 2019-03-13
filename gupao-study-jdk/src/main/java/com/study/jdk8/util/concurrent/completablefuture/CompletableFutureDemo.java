package com.study.jdk8.util.concurrent.completablefuture;


import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

/**
 *
 * @since 1.8
 * @see java.util.concurrent.CompletionStage
 * @see java.util.concurrent.CompletableFuture
 * @see java.util.function.Supplier
 * @see java.lang.Runnable
 */
public class CompletableFutureDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        completableFuture.complete("hello world");
        String s = completableFuture.get();
        System.out.println("Thread "+Thread.currentThread().getName()+" "+s);
/************************************************************************************/
        CompletableFuture hello_world = CompletableFuture.runAsync(() -> {

            System.out.println("Thread "+Thread.currentThread().getName()+" hello world");
        });
        hello_world.get();
        System.out.println("start....");

/************************************************************************************/
        CompletableFuture stringCompletableFuture = CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                return "hello";
            }
        }).thenApply(s1 -> {
            return s1+" mic";
        }).thenApply(value->{
            System.out.println("Thread "+Thread.currentThread().getName()+" "+value);
            return value;
        }).thenRun(() -> {
            System.out.println("运行结束");
        });
        System.out.println(stringCompletableFuture.isDone());
        System.out.println("start....");

    }

}
