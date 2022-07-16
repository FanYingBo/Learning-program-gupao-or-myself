package com.study.selfs.jdk5.util.concurrent.exchanger;

import java.util.concurrent.Exchanger;

/**
 * Exchanger 的线程成对存在，可以用于实现简单的 生产/消费模式
 */
public class ExchangerDemo {

    public static void main(String[] args) {
        Exchanger<String> exchanger = new Exchanger<>();

        new Thread(()->{
            String data = "A->hello";
            try {
                String exchange = exchanger.exchange(data);
                System.out.println("data:"+ data+" exchange:"+exchange);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }).start();

        new Thread(()->{
           String data = "B->hello";
           try {
               String exchange = exchanger.exchange(data);
               System.out.println("data:"+ data+" exchange:"+exchange);
           }catch (Exception e){

           }
        }).start();

        new Thread(()->{
            String data = "C->hello";
            try {
                String exchange = exchanger.exchange(data);
                System.out.println("data:"+ data+" exchange:"+exchange);
            }catch (Exception e){

            }
        }).start();

        new Thread(()->{
            String data = "D->hello";
            try {
                String exchange = exchanger.exchange(data);
                System.out.println("data:"+ data+" exchange:"+exchange);
            }catch (Exception e){

            }
        }).start();
    }

}
