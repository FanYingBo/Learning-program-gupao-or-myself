package com.study.selfs.timercall;

import java.util.List;

/**
 * excute()
 * printTime();
 */
public interface ICalled {

    public List execute();

    default void printTime(){
        this.printTime(Thread.currentThread().getName(),null);
    }

    default void printTime(String desc){
        this.printTime(Thread.currentThread().getName(),desc);
    }

    default void printTime(Thread thread){
        this.printTime(thread.getName(),null);
    }
    default void printTime(Thread thread,String desc){
       this.printTime(thread.getName(),desc);
    }

    default void printTime(String threadName,String desc){
        long start = System.currentTimeMillis();
        List execute = execute();
        long end = System.currentTimeMillis()-start;
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(threadName).append("]");
        if(desc != null){
            sb.append(" ").append(desc).append(" ");
        }
        sb.append("Execute used time：").append(end);
        System.out.println(sb.toString());
        if(execute != null){
            System.out.println("size："+execute.size());
            System.out.println("list："+execute);
        }
    }

}
