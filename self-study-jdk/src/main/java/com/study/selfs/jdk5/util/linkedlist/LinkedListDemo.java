package com.study.selfs.jdk5.util.linkedlist;

import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @since 1.2
 * @see java.util.LinkedList
 */
public class LinkedListDemo {

    public static void main(String[] args) {
        LinkedList<String> linkedList = new LinkedList();
        linkedList.add("2343212");
        linkedList.add("2342212");
        linkedList.get(0);
        linkedList.remove(0);

        linkedList.size();
        System.out.println(linkedList);

        Iterator<String> iterator = linkedList.iterator();
        while(iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }
}
