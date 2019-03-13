package com.study.gupao.designmode.singleton;

import com.study.gupao.designmode.singleton.eager.EagerSingleton;
import com.study.gupao.designmode.singleton.inner.InnerStaticClassSingleton;

import java.util.concurrent.CountDownLatch;

public class SingletonDemo {


    public static void main(String[] args) throws InterruptedException {
        int count = 10000;

        CountDownLatch countDownLatch = new CountDownLatch(count);
        for (int i = 0;i < count; i++){
            new Thread(() -> {
                countDownLatch.countDown();
                EagerSingleton instance = EagerSingleton.getInstance();

//                InnerStaticClassSingleton instance = InnerStaticClassSingleton.getInstance();
                System.out.printf("[线程：%s] 实例：%s \n",Thread.currentThread().getName(),instance);
            }).start();
        }
        countDownLatch.await();



    }
}
