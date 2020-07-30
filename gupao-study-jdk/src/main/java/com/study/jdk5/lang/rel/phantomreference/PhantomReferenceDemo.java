package com.study.jdk5.lang.rel.phantomreference;


import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

/**
 * 幻影引用
 */
public class PhantomReferenceDemo {

    public static void main(String[] args) {
        byte[] cacheByte = new byte[66*1024*1024];
        ReferenceQueue referenceQueue = new ReferenceQueue();
        PhantomReference phantomReference = new PhantomReference(cacheByte, referenceQueue);
        System.out.println(phantomReference.get());
    }
}
