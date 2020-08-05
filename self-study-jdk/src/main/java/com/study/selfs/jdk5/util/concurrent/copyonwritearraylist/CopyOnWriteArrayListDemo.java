package com.study.selfs.jdk5.util.concurrent.copyonwritearraylist;

import java.util.ListIterator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @since 1.5
 * @see java.util.concurrent.CopyOnWriteArrayList
 * @see java.util.concurrent.CopyOnWriteArrayList#add(Object) 通过ReetrantLock.lock()加锁，然后Arrays.copyOf()扩容
 * @see java.util.concurrent.CopyOnWriteArrayList#remove(Object)
 */
public class CopyOnWriteArrayListDemo {

    public static void main(String[] args) {
        CopyOnWriteArrayList<String> copyOnWriteArrayList = new CopyOnWriteArrayList();
        copyOnWriteArrayList.add("dasdasda");
        copyOnWriteArrayList.add("dsadasad");

        copyOnWriteArrayList.get(0);
        ListIterator<String> stringListIterator = copyOnWriteArrayList.listIterator();


    }
}
