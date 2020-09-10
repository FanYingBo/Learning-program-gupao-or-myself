package com.study.selfs.jvm;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * JVM opt  -Xmx1M -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=D:\testdumpfile.log
 * IBM HeapAnalyzer 分析
 * java.lang.OutOfMemoryError: Java heap space
 * Dumping heap to D:\testdumpfile.log ...
 * Heap dump file created [1471347 bytes in 0.119 secs]
 */
public class OutOfMemoryDemo {

    public static void main(String[] args) {
        TestClass testClass = new TestClass();
        for(int i = 0;i < 100000;i++){
            testClass.put(i,new TestEntity());
        }
    }

}

class TestClass{

    public Map<Integer,TestEntity> dataMap;

    public TestClass() {
        this.dataMap = new HashMap<>();
    }

    public void put(int num, TestEntity entity ){
        dataMap.put(num,entity);
    }
}

class TestEntity{

}
