package com.study.jdk5.lang.rel.softreference;

import java.lang.ref.SoftReference;

/**
 * 软引用是用来描述一些还有用但并非必须的对象。对于软引用关联着的对象，在系统将要发生内存溢出异常之前，
 * 将会把这些对象列进回收范围进行第二次回收。如果这次回收还没有足够的内存，才会抛出内存溢出异常。
 */
public class SoftReferenceDemo {
    public static void main(String[] args) {
        byte[] cacheByte = new byte[100*1024*1024];
        SoftReference softReference = new SoftReference(cacheByte);
        cacheByte = null;
        System.out.println("第一次回收前："+cacheByte);
        System.out.println("第一次回收前："+softReference.get());
        System.gc();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("第一次回收后："+cacheByte);
        System.out.println("第一次回收后："+softReference.get());
        byte[] newByte = new byte[100*1024*1024];
        System.out.println("第二次回收后："+cacheByte);
        System.out.println("第二次回收后："+softReference.get());
    }
}
