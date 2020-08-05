package com.study.selfs.jdk5.util.treemap;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * TreeMap 可以用于有序的数据结构，可以方便取得所在的区间
 * @since 1.2
 * @see java.util.TreeMap  红黑树
 */
public class TreeMapDemo {

    public static void main(String[] args) {
        TreeMap<Integer,String> treeMap = new TreeMap<>();
        treeMap.put(2,"dadaq");
        treeMap.put(1,"231212");
        treeMap.put(3,"231211");
        treeMap.put(4,"dadaq");

        // 1.getEntry
        treeMap.get(1);
        treeMap.remove(2);

        Map.Entry<Integer, String> higherEntry = treeMap.higherEntry(2);
        System.out.println(higherEntry);
        Map.Entry<Integer, String> floorEntry = treeMap.floorEntry(1);// 小于最小值则取到空值
        System.out.println(floorEntry);
        Set<Integer> integers = treeMap.keySet();
        Iterator<Integer> iterator = integers.iterator();
        while(iterator.hasNext()){
            iterator.next();
        }
        // 1111 0100   00001111&00100001
        System.out.println(00001111&00100001);

    }
}
