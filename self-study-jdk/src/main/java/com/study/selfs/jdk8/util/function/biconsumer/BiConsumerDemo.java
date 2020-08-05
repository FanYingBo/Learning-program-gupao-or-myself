package com.study.selfs.jdk8.util.function.biconsumer;

import java.util.function.BiConsumer;

/**
 * 接收两个参数
 * {@link java.util.function.BiConsumer}
 */
public class BiConsumerDemo {
    public static void main(String[] args) {
        BiConsumer<Integer,String> stringBiConsumer = (ints,str) ->{
            System.out.println(str+"：" + ints);
        };
        stringBiConsumer.andThen((ints,str)->{
            System.out.println("next:"+ints);
        }).accept(1,"first");
    }
}
