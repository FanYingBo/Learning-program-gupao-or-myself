package com.study.selfs.jdk7.util.concurrent.forkjoinpool;


import com.study.selfs.jdk7.util.concurrent.recursiveaction.CountedTaskWithRecursiveAndDichotomy;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.LongAdder;

/**
 * @since java 7
 * @see java.util.concurrent.ForkJoinPool
 */
public class ForkJoinPoolDemo {

    public static void main(String[] args) {
        testParallelCompute();//通过递归和二分法求数值
    }
    private static void test1(){
        System.out.println("当前系统的核心数："+Runtime.getRuntime().availableProcessors());
        System.out.println("当前系统的核心数："+(ForkJoinPool.commonPool().getParallelism()+1));
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<String> submit = forkJoinPool.submit(new Callable<String>() {

            @Override
            public String call() throws Exception {
                return "hello wold";
            }
        });
        try {
            String s = submit.get();
            System.out.println(s);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过递归和二分法求数值
     */
    private static void testParallelCompute(){
        ForkJoinPool forkJoinPool = new ForkJoinPool();

        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        LongAdder longAdder = new LongAdder();

        CountedTaskWithRecursiveAndDichotomy addTask = new CountedTaskWithRecursiveAndDichotomy(integers,longAdder);

        forkJoinPool.invoke(addTask);

        forkJoinPool.isShutdown();

        System.out.println("累加的结果："+longAdder);
    }

}


