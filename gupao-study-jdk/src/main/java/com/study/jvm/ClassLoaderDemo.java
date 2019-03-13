package com.study.jvm;

/**
 *
 *
 */
public class ClassLoaderDemo {

    static int demo ;

    static{
        demo = 1;
        System.out.println(demo);
    }

    public static void main(String[] args) {
        System.out.println(demo);
    }
//    static int demo ;
}
