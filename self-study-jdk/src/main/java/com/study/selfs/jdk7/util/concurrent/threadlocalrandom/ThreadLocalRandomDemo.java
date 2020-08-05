package com.study.selfs.jdk7.util.concurrent.threadlocalrandom;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.*;
import java.util.stream.DoubleStream;

/**
 * 
 * @since 1.7
 * @see java.util.concurrent.ThreadLocalRandom
 */
public class ThreadLocalRandomDemo {

    public static void main(String[] args) {
        ThreadLocalRandom current = ThreadLocalRandom.current();
        DoubleStream doubles = current.doubles();

//        Consumer<Double> consumer = value->{};
        Supplier<Double> supplier = ()->23d;
        BiConsumer<Double,Double> consumer_ = (value1,value2) -> {
            System.out.println(value1+value2);
        };
        BiConsumer<Double,Double> consumer_1 = (value1,value2)->{
            System.out.println(value1+value2);
        };
        ObjDoubleConsumer<Double> objDoubleConsumer = (value,value1)->{
            System.out.println(value1+value1);
        };
        Double collect = doubles.collect(supplier, objDoubleConsumer, consumer_1);
//        PrimitiveIterator.OfDouble iterator = doubles.iterator();
//        Double next = iterator.next();

        System.out.println(collect);




    }


}


