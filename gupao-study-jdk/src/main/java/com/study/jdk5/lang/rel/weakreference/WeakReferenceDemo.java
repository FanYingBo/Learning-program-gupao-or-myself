package com.study.jdk5.lang.rel.weakreference;

import java.lang.ref.WeakReference;

/**
 创建阶段(Created)
 应用阶段(In Use)
 不可见阶段(Invisible)
 不可达阶段(Unreachable)
 收集阶段(Collected)
 终结阶段(Finalized)
 对象空间重分配阶段(De-allocated)
 *
 *弱引用也是用来描述非必须对象的，他的强度比软引用更弱一些，
 * 被弱引用关联的对象，在垃圾回收时，如果这个对象只被弱引用关联（没有任何强引用关联他），那么这个对象就会被回收。
 * 垃圾回收时被回收
 */
public class WeakReferenceDemo {

    public static void main(String[] args) {
        byte[] cacheByte = new byte[66*1024*1024];
        WeakReference weakReference = new WeakReference(cacheByte);
        System.out.println("第一次回收前："+cacheByte);
        System.out.println("第一次回收前："+weakReference.get());
        try{
            byte[] cacheByte1 = new byte[100*1024*1024];
        }catch (Error error){
            error.printStackTrace();
            cacheByte = null;
        }
        System.out.println("第一次回收后："+cacheByte);
        System.out.println("第一次回收后："+weakReference.get());
        System.gc();
        System.out.println("第二次回收后："+cacheByte);
        System.out.println("第二次回收后："+weakReference.get());
    }
}
