package com.study.selfs.jdk5.util.list;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @since 1.2
 * @see java.util.List
 * @see java.util.Collection
 * @see java.lang.Iterable
 */
public class ListDemo {

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        System.out.println(list);
        remove(list);
        System.out.println(list);

        StringBuilder sb = new StringBuilder("wqw");
        System.out.println(sb.toString());
        genStr(sb);
        System.out.println(sb.toString());

        AtomicInteger integer = new AtomicInteger(2);
        System.out.println(integer);
        genInt(integer);
        System.out.println(integer);
    }

    private static void remove(List<Integer> list){
        Iterator<Integer> iterator = list.iterator();
        while(iterator.hasNext()){
            Integer next = iterator.next();
            if(next.intValue() == 3){
                iterator.remove();
            }
        }
    }

    private static void genStr(StringBuilder sb){
        sb.append("hello world");
    }

    private static void genInt(AtomicInteger sb){
        sb.addAndGet(1);
    }

}
