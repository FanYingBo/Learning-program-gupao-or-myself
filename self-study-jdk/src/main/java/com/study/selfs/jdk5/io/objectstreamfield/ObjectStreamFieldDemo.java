package com.study.selfs.jdk5.io.objectstreamfield;

import java.io.ObjectStreamField;

/**
 * jdk 1.2 ObjectStreamField
 * 该类是描述串行化类中串行化字段的类
 * 该类属于‘常用类’（在封装好的Object操作类中大量使用该类），
 * 该类实质上是类字段的一种抽象化表现，但同时也和Field类有所不同，
 * 由于该类单纯针对串行化字段，所以提供的功能和属性少于Field类，
 * 属于一种更高层次的单一功能性抽象
 */
public class ObjectStreamFieldDemo {

    public static void main(String[] args) {
        ObjectStreamField objectStreamField = new ObjectStreamField("sayHello",String.class);
        System.out.println("获取名称："+objectStreamField.getName());
        System.out.println("获得类型："+objectStreamField.getTypeString());
        System.out.println("获得类型标识符："+objectStreamField.getTypeCode());
        System.out.println("偏移量："+objectStreamField.getOffset());// 获得偏移量

    }
}
