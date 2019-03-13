package com.study.jdk5.util.concurrent.callable;

import java.util.concurrent.Callable;


/**
 *
 */
public class CallableDemo implements Callable<String> {
    @Override
    public String call() throws Exception {

        return "[线程 "+Thread.currentThread().getName()+"] "+"hallo word";
    }

    public static void main(String[] args) {
        CallableDemo callableDemo = new CallableDemo();
        try {
            String call = callableDemo.call();
            System.out.println(call);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
