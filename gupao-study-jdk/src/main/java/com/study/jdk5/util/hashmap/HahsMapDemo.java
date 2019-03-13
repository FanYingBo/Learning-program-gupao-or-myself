package com.study.jdk5.util.hashmap;

import java.util.HashMap;

/**
 * @since 1.2
 * @see java.util.HashMap#TREEIFY_THRESHOLD=8  数组+链表的方式 阈值大于8时链表转化为平衡二叉树（红黑树）
 *
 */
public class HahsMapDemo {

    public static void main(String[] args) {
        HashMap<String,String> hashMap = new HashMap<>();

        // [2,2,1,3]
        //  0 1 2 3
//        int[] ints = {2,2,1,3};
//        System.out.println(ints[0]);
        hashMap.put("didhevnnfjfja","dadaq");// 0101 1010   0000000000000000000000
        hashMap.put("wj1hhheq122fss","231212");
        hashMap.put("rdasdqweffawe123","231211");
        hashMap.put("233fwe334ww2","dadaq");
        hashMap.put("sadwer345w","231212");
        hashMap.put("784ederfgghhee","231211");
        hashMap.put("44232eewrrtydssdf","dadaq");
        hashMap.put("242dsfasdeer","231212");

        hashMap.put("daswqefsrefda","dadaq");
        hashMap.put("sadwr323sfsht5","231211");

        hashMap.put("feqeqwe323dfdsfer","231212");
        hashMap.put("r3rddsf323fewff3","231211");


        hashMap.get("r3rddsf323fewff3");

        hashMap.keySet().iterator();
        hashMap.entrySet().iterator();

    }
}
