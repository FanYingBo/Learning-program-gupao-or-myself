package com.study.jdk8.util.concurrent.atomic;

import java.util.concurrent.atomic.LongAdder;

/**
 * @since jdk 1.8
 * @see java.util.concurrent.atomic.LongAdder
 * 1.多线程操作时
 */
public class LongAdderDemo {

    public static void main(String[] args) {
        LongAdder longAdder = new LongAdder();
        longAdder.add(122);
        longAdder.increment();
        longAdder.sumThenReset();
        System.out.println(longAdder.intValue());
        System.out.println(longAdder.longValue());
    }

}
