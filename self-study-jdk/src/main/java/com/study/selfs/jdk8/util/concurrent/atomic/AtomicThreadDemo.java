package com.study.selfs.jdk8.util.concurrent.atomic;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;

/**
 * Atomic Performance Test
 * LongAdder AtomicInteger synchronized
 * @see java.util.concurrent.atomic.LongAdder
 * @see java.util.concurrent.atomic.AtomicInteger
 *
 */
public class AtomicThreadDemo extends Thread{

    public static AtomicInteger atomicInteger = new AtomicInteger();

    public static LongAdder longAdder = new LongAdder();

    private CountDownLatch countDownLatch;

    public static int intVal = 0;

    public OptType optType;

    public AtomicThreadDemo(OptType type, String threadName, CountDownLatch countDownLatch){
        super(threadName);
        this.optType = type;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        countDownLatch.countDown();
        switch (optType){
            case AOTMICINT:
                atomicIntegerInc();
                break;
            case LONGADDER:
                longAdderInc();
                break;
            case INT:
                intInc();
                break;
            default:
                break;
        }
    }

    public void atomicIntegerInc(){
        atomicInteger.getAndIncrement();
    }

    public void longAdderInc(){
        longAdder.increment();
    }

    public synchronized void intInc(){
        intVal ++;
    }


    public static enum OptType {

        AOTMICINT(0,"atomic_integer"),LONGADDER(1,"longaddr"),INT(2,"synchronized");

        private int value;

        private String name;

        OptType(int value,String name){
            this.value = value;
            this.name = name;
        }

        public int intValue(){
            return value;
        }

        public String getName(){
            return name;
        }
    }


}
