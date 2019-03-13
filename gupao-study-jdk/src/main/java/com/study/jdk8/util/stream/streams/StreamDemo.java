package com.study.jdk8.util.stream.streams;

import com.study.test.util.ListUtils;
import com.study.timercall.ICalled;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamDemo {
    public List<Integer> intList ;

    public List<String> stringList ;

    @Before
    public void initList(){
        intList = ListUtils.getRanIntList(100,100);
        stringList = ListUtils.getRanStr(100,3);

    }
    //    @Test
    public void paralleStreamSort(){
        long start = System.currentTimeMillis();
        intList.parallelStream().sorted((int1, int2) -> Integer.compare(int1, int2)).collect(Collectors.toList());
        System.out.println("Collections Sort 耗时："+(System.currentTimeMillis()-start));// 较 变慢5倍
        System.out.println(intList);
    }

    /**
     * @see java.util.stream.Stream#map(Function)
     */
//    @Test
    public void streamMapOpt(){
        ICalled iCalled = new ICalled(){
            @Override
            public List excute() {
                List<String> collect = stringList.stream().map(String::toUpperCase).collect(Collectors.toList());
                return collect;
            }
        };
        iCalled.printTime(Thread.currentThread());
    }

    /**
     * @see java.util.stream.Stream#flatMap(Function)
     */
//    @Test
    public void streamflatMapOpt(){
        ICalled iCalled = new ICalled(){
            @Override
            public List excute() {
                Stream<List<Integer>> inputStream = Stream.of(
                        Arrays.asList(1),
                        Arrays.asList(2, 3),
                        Arrays.asList(4, 5, 6)
                );
                Stream<Integer> outputStream = inputStream.flatMap((childList) -> childList.stream());
                System.out.println(Arrays.toString(outputStream.toArray()));
                return null;
            }
        };
        iCalled.printTime(Thread.currentThread());
    }

    /**
     * @see java.util.stream.Stream#filter(Predicate)
     */
//    @Test
    public void streamFilter(){
        ICalled iCalled = new ICalled(){
            @Override
            public List excute() {
                return intList.stream().filter(integer -> integer%2 == 0).collect(Collectors.toList());
            }
        };
        iCalled.printTime(Thread.currentThread());
    }

    @Test
    public void streamForEach(){
        ICalled iCalled_ = new ICalled(){
            @Override
            public List excute() {
                for(int i=0;i<stringList.size();i++){
                    System.out.println(stringList.get(i));
                }
                return null;
            }
        };
        iCalled_.printTime(Thread.currentThread());
        ICalled iCalled = new ICalled(){
            @Override
            public List excute() {
                stringList.stream().forEach(s -> System.out.println(s));
                return null;
            }
        };
        iCalled.printTime(Thread.currentThread());
    }

}
