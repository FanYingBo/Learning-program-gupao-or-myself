package com.study.test;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public class HashMapProblemDemo {

    private static HashMap<Integer,String> hashMap = new HashMap();
    private static TreeMap<Integer,String> treeMap = new TreeMap<>();

    public static void main(String[] args) {
//        System.out.println(hash("dadadwwww"));
//        endlessLoop();
//        System.out.println(hashMap.get(1));
        treeMapTest();
    }


    /**
     * treeMap
     */
    private static void treeMapTest(){
        for(int i = 0;i < 8;i++){
            treeMap.put(i,"hash_"+i);
        }
        System.out.println(treeMap.lastEntry().getKey());
        System.out.println(treeMap.firstEntry().getKey());
        System.out.println(treeMap);
//        System.out.println(treeMap.floorEntry(2).getKey());
    }

    private static void endlessLoop(){
        for(int i = 0;i < 2;i++){
//            new Thread(() ->{
                hashMap.put(i,"hash_"+i);
//            }).start();
        }
        hashMap.put(1,"dsaddd");
    }
    static final int hash(Object key) {
        int h = (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
        return h;
    }

}



