package com.study.selfs.jdk5.util.hashtable;

import java.util.Hashtable;

/**
 * 源码分析
 * @since 1.0
 * @see java.util.Hashtable
 */
public class HashTableDemo {

    public static void main(String[] args) {
        Hashtable<Integer,String> hashtable = new Hashtable<>();

        hashtable.put(2,"dadaq");
        hashtable.put(1,"231212");

        hashtable.get(1);

        hashtable.keySet().iterator();
        hashtable.entrySet().iterator();
    }

}
