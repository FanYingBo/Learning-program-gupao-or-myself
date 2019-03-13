package com.study.timercall;

import java.util.List;

/**
 * excute()
 * printTime();
 */
public interface ICalled {

    public List excute();

    public default void printTime(Thread thread){
       this.printTime(thread.getName());
    }

    public default void printTime(String threadName){
        long start = System.currentTimeMillis();
        List excute = excute();
        System.out.println("Thread name："+threadName+" Excute used time："+(System.currentTimeMillis()-start));
        if(excute != null){
            System.out.println("size："+excute.size());
            System.out.println("list："+excute);
        }
    }

}
