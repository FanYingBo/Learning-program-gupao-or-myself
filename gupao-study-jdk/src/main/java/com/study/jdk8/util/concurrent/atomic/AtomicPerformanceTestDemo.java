package com.study.jdk8.util.concurrent.atomic;

import com.study.test.util.PrintUtils;
import com.study.timercall.ICalled;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class AtomicPerformanceTestDemo {

    private static int count = 100;

    public static void main(String[] args) {

        testInc(AtomicThreadDemo.OptType.AOTMICINT);// 13335 14454

//        testInc(AtomicThreadDemo.OptType.LONGADDER);//13427

//        testInc(AtomicThreadDemo.OptType.INT);//13085
    }
    private static void testInc(AtomicThreadDemo.OptType optType){
        Thread thread = new Thread(() -> {
            ICalled iCalled_1 = new ICalled() {
                CountDownLatch countDownLatch  = new CountDownLatch(count);
                @Override
                public List excute() {
                    List<Thread> threads = new ArrayList<>();
                    for (int i = 0; i < count; i++) {
                        AtomicThreadDemo atomicThreadDemo = new AtomicThreadDemo(optType, optType.getName() + "_thread_" + i, countDownLatch);
                        threads.add(atomicThreadDemo);
                        atomicThreadDemo.start();
                    }
                    try {
                        countDownLatch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return threads;
                }
            };
            iCalled_1.printTime(optType.getName() + "_thread value：");
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        PrintUtils.print("int value " +  AtomicThreadDemo.atomicInteger.intValue());
        PrintUtils.print("long value " +  AtomicThreadDemo.longAdder.longValue());
        PrintUtils.print("unsafe value " +  AtomicThreadDemo.intVal);
    }
}
