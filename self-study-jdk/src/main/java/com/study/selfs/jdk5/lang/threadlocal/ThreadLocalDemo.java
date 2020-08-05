package com.study.selfs.jdk5.lang.threadlocal;

/**
 * @see java.lang.ThreadLocal
 */
public class ThreadLocalDemo {

    private static ThreadLocal threadLocal = new ThreadLocal();

    public static void main(String[] args) {

        Thread thread = new Thread(() -> {
            if(threadLocal.get() == null){
                threadLocal.set("thread one");
                System.out.println(Thread.currentThread().getName()+" "+threadLocal.get());//
            }

        });
        Thread thread1 = new Thread(() -> {
            if(threadLocal.get() == null){
                threadLocal.set("thread two");
                System.out.println(Thread.currentThread().getName()+" "+threadLocal.get());
            }
        });
        thread.start();
        thread1.start();
    }

}

