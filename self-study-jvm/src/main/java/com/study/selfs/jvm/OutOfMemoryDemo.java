package com.study.selfs.jvm;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * JVM opt  -Xmx1M -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=D:\testdumpfile.log
 *
 * java.lang.OutOfMemoryError: Java heap space
 * Dumping heap to D:\testdumpfile.log ...
 * Heap dump file created [1471347 bytes in 0.119 secs]
 */
public class OutOfMemoryDemo {

    public static void main(String[] args) {
        TestClass testClass = new TestClass();
        testClass.put(2,new byte[1024 * 1024]);
        testClass.put(2,new byte[1024 * 1024]);
    }

}

class TestClass{

    public Map<Integer,byte[]> dataMap;

    public TestClass() {
        this.dataMap = new HashMap<>();
    }

    public void put(int num, byte[] bytes ){
        dataMap.put(num,bytes);
    }
}
