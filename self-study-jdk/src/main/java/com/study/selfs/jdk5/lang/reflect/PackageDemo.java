package com.study.selfs.jdk5.lang.reflect;

import com.study.selfs.jdk5.lang.reflect.entity.Member;

/**
 * java.lang.reflect.Package
 */
public class PackageDemo {
    public static void main(String[] args) {
        // class
        Class<?> clazz = Member.class;
        // 获取包
        Package aPackage = clazz.getPackage();
        System.out.println("aPackage name："+aPackage.getName());
    }
}
