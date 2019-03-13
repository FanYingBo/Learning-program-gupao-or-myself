package com.study.gupao.reactive;

public class ThreadDemo {

    public static void main(String[] args) throws InterruptedException {
        print("主线程开始");
        final Boolean[] flag = {false};
        Thread thread = new Thread(() -> {
            print("sub-thread");
            flag[0] = true;
        },"sub-thread");

        thread.start();

        thread.join();// Waits for this thread to die.
        print("flag " + flag[0]);
        print("主线程结束");
    }

    public static void print(String message){
        System.out.printf("[线程：%s] %s\n",Thread.currentThread().getName(),message);
    }
}
