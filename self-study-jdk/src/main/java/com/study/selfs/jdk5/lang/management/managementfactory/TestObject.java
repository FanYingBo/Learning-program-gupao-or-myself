package com.study.selfs.jdk5.lang.management.managementfactory;

public class TestObject implements TestObjectMXBean{
    @Override
    public void printHello(String name) {
        System.out.println("Hello, "+name);
    }
}
