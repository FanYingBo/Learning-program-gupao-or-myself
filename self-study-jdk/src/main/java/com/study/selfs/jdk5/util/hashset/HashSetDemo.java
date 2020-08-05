package com.study.selfs.jdk5.util.hashset;

import java.util.HashSet;
import java.util.Iterator;

/**
 * 源码
 * @see java.util.HashSet
 */
public class HashSetDemo {

    public static void main(String[] args) {
        HashSet<Integer> hashSet = new HashSet<>();
        hashSet.add(212);
        hashSet.add(211);
        hashSet.add(213);
//        hashSet.contains(213);

        Iterator<Integer> iterator = hashSet.iterator();
        while(iterator.hasNext()){
            System.out.println(iterator.next());
        }
        System.out.println(hashSet);
    }
}
