package com.study.selfs.jdk5.lang.rel.softreference;

import java.lang.ref.SoftReference;

/**
 * 软引用是用来描述一些还有用但并非必须的对象。对于软引用关联着的对象，在系统将要发生内存溢出异常之前，
 * 将会把这些对象列进回收范围进行第二次回收。如果这次回收还没有足够的内存，才会抛出内存溢出异常。
 * 将要发生内存溢出时回收
 * -Xmx100m -XX:+PrintGC -XX:+PrintGCDetails
 */
public class SoftReferenceDemo {
    public static void main(String[] args) {
        long totalMemory = Runtime.getRuntime().totalMemory();
        System.out.println("总内存："+totalMemory/(1024*1024));
        long maxMemory = Runtime.getRuntime().maxMemory();
        System.out.println("最大内存："+maxMemory/(1024*1024));
        SoftReference softReference = new SoftReference(new byte[66*1024*1024]);
        long freeMemory = Runtime.getRuntime().freeMemory();
        System.out.println("空闲内存："+freeMemory/(1024*1024));
        System.gc(); // 主动垃圾回收时（内存充足）不会回收
        System.out.println("第一次回收后："+softReference.get());
        try{
            byte[] newByte = new byte[100*1024*1024]; // 内存不足时，会回收
        }catch (Error e){
            e.printStackTrace();
        }
        freeMemory = Runtime.getRuntime().freeMemory();
        System.out.println("空闲内存："+freeMemory/(1024*1024));
        System.out.println("第二次回收后："+softReference.get());
    }
}
