package com.study.selfs.jdk5.util.collections;

import com.study.selfs.test.util.ListUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * {@link java.util.Collections}
 */
public class CollectionsDemo {

    private List<String> strings;

    private List<Integer> ints;

    @Before
    public void init(){
        strings = ListUtils.getRanStr(5,1);
        ints = ListUtils.getRanIntList(5,100);
    }

    /**
     * {@link Collections#binarySearch(List, Object)}
     */
    @Test
    public void testCollectionsBinarySearch(){
        System.out.println(strings);
        int binarySearch = Collections.binarySearch(strings, "a");
        System.out.println(binarySearch);
    }

    /**
     * @since 1.8 中默认使用了归并排序
     * {@link java.util.List#sort(Comparator)}
     */
    @Test
    public void testCollectionsSort(){
        System.out.println(ints);
        Collections.sort(ints);
        System.out.println(ints);
    }
}
