package com.study.selfs.jvm;

/**
 * {@link java.lang.StackOverflowError}
 * 栈溢出 -XX:PermSize=128M  -XX:MaxPermSize=256M 非堆内存
 * 分析：这个过程发生了什么？
 *
 */
public class StackOverflowDemo {

    public static void main(String[] args) {
//        new TestMethod().sum(2);

    }
}

/**
 * 场景一：方法调用
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
 */
class TestMemory{
    public void allocate(){
        byte[] bytes = new byte[1024 * 1024];
    }
}