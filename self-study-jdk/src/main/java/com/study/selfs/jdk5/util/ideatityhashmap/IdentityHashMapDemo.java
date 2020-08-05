package com.study.selfs.jdk5.util.ideatityhashmap;

import java.util.IdentityHashMap;

/**
 * 1.数组的方式存储 object[] table    table[i]为key table[i+1]为value
 *    i为偶数 i为key的hash
 * 2.数组的初始容量默认容量大小为 64，是expectedMaxSize的大小
 * 3.key经过hash后为偶数，每次put操作对key的hash值比较,hash后的值若有值且与key不相等则table向后追加(容量不够扩容)，若相等则替换
 * 4.取值时比较要取的key所在的hash范围内的key对象引用的地址对比，若相等则返回
 * @since 1.4
 * @see java.util.IdentityHashMap
 */
public class IdentityHashMapDemo {

    public static void main(String[] args) {
        IdentityHashMap<Integer ,String> identityHashMap = new IdentityHashMap<>();
        Integer integer = new Integer(221);
        Integer integer1 = new Integer(221);// 通过 == 对比，比较的是对象引用的地址
        identityHashMap.put(integer,"ddada");
        identityHashMap.put(integer1,"dasda");// 通过 == 对比，比较的是对象引用的地址
        System.out.println(identityHashMap.get(integer));// 取值为ddada
        System.out.println(identityHashMap.size());

    }
}
