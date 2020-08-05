package com.study.selfs.jdk5.util.weakhashmap;

import java.util.WeakHashMap;

/**
 * WeakHashMap将周期性地检 查队列， 以便找出新添加的弱引用。
 * 一个弱引用进人队列意味着这个键不再被他人使用， 并 且已经被收集起来。于是， WeakHashMap将删除对应的条目
 *
 */
public class WeakHashMapDemo {

    public static void main(String[] args) {
        WeakHashMap<Object, Object> map = new WeakHashMap<>(); // 弱引用hashmap
        map.put("sd","111111");

        map.get("sd");


    }
}
