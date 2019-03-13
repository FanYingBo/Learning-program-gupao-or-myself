package com.study.jdk8.util.function.doubleconsumer;

import com.study.test.util.PrintUtils;

import java.util.concurrent.Callable;
import java.util.function.DoubleConsumer;

/**
 * @since 1.8
 * @see java.util.function.DoubleConsumer
 */
public class DoubleConsumerDemo {

    public static void main(String[] args) {
        DoubleConsumer doubleConsumer = value -> PrintUtils.print(value);
        doubleConsumer.andThen(value -> {
            System.out.println(value*value);
        }).accept(32);
    }
}
