package com.study.selfs.jvm;

/**
 *
 * 栈溢出 -XX:PermSize=128M  -XX:MaxPermSize=256M 非堆内存
 * 分析：这个过程发生了什么？
 *
 */
public class StackOverflowDemo {

    public static void main(String[] args) {
        new TestMethod().sum(2);
//        new TestMemory().allocate();
    }
}

/**
 * 场景一：方法调用
 * {@link java.lang.StackOverflowError}
 */
class TestMethod{

    TestMethod (){

    }

    public int sum(int i){
        i+=1;
        return sum(i);
    }
}

/**
 * 场景二：
 * 这里能否栈溢出呢？
 * {@link java.lang.OutOfMemoryError}
 * JVM opt: -Xmx10M
 */
class TestMemory{

    public void allocate(){
        byte[] bytes = new byte[1024 * 1024 * 10];
    }
}