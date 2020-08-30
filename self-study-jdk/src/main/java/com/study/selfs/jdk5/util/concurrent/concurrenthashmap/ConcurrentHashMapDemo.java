package com.study.selfs.jdk5.util.concurrent.concurrenthashmap;


import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * ConcurrentHashMap 对 map进行添加操作的时候会
 * @since java5
 * @see java.util.concurrent.ConcurrentHashMap
 */
public class ConcurrentHashMapDemo {
    public static void main(String[] args) {
        ConcurrentHashMap<Integer, String> concurrentHashMap = new ConcurrentHashMap();
        concurrentHashMap.put(1,"ab");
        concurrentHashMap.get(1);
        concurrentHashMap.size();
    }
}
