package com.study.mybatis;

import java.lang.reflect.Method;

public class InnerStaticClass {

    public static class InnerSttaticClassChild{

        public void print(){
            System.out.println(" inner class method static");
        }
    }

    public static void main(String[] args) throws Exception {
//        InnerStaticClass innerStaticClass = new InnerStaticClass();
//        innerStaticClass.run();
//
//        InnerClass innerClass = new InnerClass();
//        innerClass.run();
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        Class<?> aClass = classLoader.loadClass("com.study.mybatis.InnerClass");
        Method run = aClass.getMethod("run", new Class[]{});
        Object o = aClass.newInstance();
        run.invoke(o,new Object[]{});
    }

    public void run(){
        InnerStaticClass.InnerSttaticClassChild innerSttaticClassChild = new InnerSttaticClassChild();
        innerSttaticClassChild.print();
    }
}
