package com.study.selfs.jdk8.util.function.function;

import java.util.function.Function;

/**
 *
 * @since 1.8
 * @see java.util.function.Function
 */
public class FunctionDemo {

    public static void main(String[] args) {
        Function<Integer,String> function = r -> "first";
        System.out.println(function.apply(1));
    }
}
