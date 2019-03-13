package com.study.jdk5.util.collection;

import com.study.test.util.HashMapUtils;
import com.study.test.util.ListUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

/**
 * @since 1.2
 * @see java.util.Collection
 * @see java.util.Collections
 */
public class CollectionsDemo {

    public List<Integer> intList ;

    public List<String> stringList ;

    public Map<Integer, String> hashMap;


    @Before
    public void initList(){
        intList = ListUtils.getRanIntList(100,100);
        stringList = ListUtils.getRanStr(100,3);
        hashMap = HashMapUtils.getRanMap(5);
    }

    @Test
    public void collectionsFun(){
        // 获得一个不可变集合，对集合做增删改会报UnsupportedOperationException异常
        List<Integer> integers = Collections.unmodifiableList(intList);
        // 获得一个线程安全的集合
        List<Integer> blockList = Collections.synchronizedList(intList);
        //
        Map<Integer, String> integerStringMap = Collections.synchronizedMap(hashMap);

    }


    /**
     * 通过Collections.sort 排序
     */
//    @Test
    public void collectionSort(){
        long start = System.currentTimeMillis();
        Collections.sort(intList, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Integer.compare(o1,o2);//-1 0 1
            }
        });
        System.out.println("Collections Sort 耗时："+(System.currentTimeMillis()-start));//22
        System.out.println(intList);
    }

    /**
     *lambda表达式
     */
//    @Test
    public void lambdaSort(){
        long start = System.currentTimeMillis();
        Collections.sort(intList, (int1,int2)->Integer.compare(int1,int2));
        System.out.println("Collections Sort 耗时："+(System.currentTimeMillis()-start));// 较 变慢5倍
        System.out.println(intList);
    }


}



