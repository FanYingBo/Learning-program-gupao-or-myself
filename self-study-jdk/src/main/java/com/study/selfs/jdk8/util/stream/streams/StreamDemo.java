package com.study.selfs.jdk8.util.stream.streams;

import com.study.selfs.test.util.ListUtils;
import com.study.selfs.timercall.ICalled;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *  1.遍历对比 {@link #traversePerformance()}
 *  2.reduce {@link #reducePerformance}当数据量超过一定的临界值（测试的list集合 超过2000000的大小时） 使用
 *      {@link Stream#parallel()} or {@link List#parallelStream()}
 */
public class StreamDemo {
    public List<Integer> intList ;

    public List<String> stringList ;

    @Before
    public void initList(){
        intList = ListUtils.getRanIntList(30000000,1000);
        stringList = ListUtils.getRanStr(1000000,3);
    }
    @Test
    public void parallelStreamSort(){
        long start = System.currentTimeMillis();
        intList.parallelStream().sorted(Integer::compare).collect(Collectors.toList());
        System.out.println("Collections Sort 耗时："+(System.currentTimeMillis()-start));// 较 变慢5倍
        System.out.println(intList);
    }

    @Test
    public void traversePerformance(){
        ICalled iCalled_ = ()->{
            for(int i=0;i<stringList.size();i++){
                stringList.get(i);
            }
            return null;
        };
        iCalled_.printTime(Thread.currentThread(),"normal for");
        ICalled called_ = () ->{
            for(String string : stringList){
                // no
            }
            return null;
        };
        called_.printTime(Thread.currentThread(),"normal for each");
        ICalled called_1 = () ->{
            Iterator<String> iterator = stringList.iterator();
            while(iterator.hasNext()){
                String next = iterator.next();
            }
            return null;
        };
        called_1.printTime(Thread.currentThread(),"iterator");
        ICalled called = () ->{
            stringList.stream().forEach(s -> {});
            return null;
        };
        called.printTime(Thread.currentThread(),"stream for each");
        ICalled called_2 = () ->{
            stringList.parallelStream().forEach(s -> {});
            return null;
        };
        called_2.printTime(Thread.currentThread(),"parallel stream for each");
    }
    @Test
    public void reducePerformance(){
        final int[] min = {Integer.MAX_VALUE};
        ICalled iCalled_ = ()->{
            for(int i=0;i<intList.size();i++){
                Integer integer = intList.get(i);
                if(min[0] > integer){
                    min[0] = integer;
                }
            }
            return null;
        };
        iCalled_.printTime(Thread.currentThread(),"normal for");
        ICalled called_ = () ->{
            for(int num : intList){
                if(min[0] > num){
                    min[0] = num;
                }
            }
            System.out.println(min[0]);
            return null;
        };
        called_.printTime("normal for each");
        ICalled called_1 = () ->{
            Iterator<Integer> iterator = intList.iterator();
            while(iterator.hasNext()){
                Integer num = iterator.next();
                min[0] = Math.min(min[0],num);
            }
            System.out.println(min[0]);
            return null;
        };

        called_1.printTime("iterator");
        ICalled called = () ->{
            intList.stream().reduce(min[0], (i,j) -> Math.min(i,j));
            System.out.println(min[0]);
            return null;
        };
        called.printTime("stream for each");
        ICalled called_2 = () ->{
            intList.parallelStream().reduce(min[0], (i,j) -> Math.min(i,j));
            System.out.println(min[0]);
            return null;
        };
        called_2.printTime("parallel stream for each");
    }
    /**
     * @see java.util.stream.Stream#map(Function)
     */
    @Test
    public void streamMapOpt(){
        ICalled iCalled = new ICalled(){
            @Override
            public List execute() {
                List<String> collect = stringList.stream().map(String::toUpperCase).collect(Collectors.toList());
                return collect;
            }
        };
        iCalled.printTime(Thread.currentThread());
    }

    /**
     * @see java.util.stream.Stream#flatMap(Function)
     */
    @Test
    public void streamflatMapOpt(){
        ICalled iCalled = new ICalled(){
            @Override
            public List execute() {
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
    @Test
    public void streamFilter(){
        ICalled iCalled = new ICalled(){
            @Override
            public List execute() {
                return intList.stream().filter(integer -> integer%2 == 0).collect(Collectors.toList());
            }
        };
        iCalled.printTime(Thread.currentThread());
    }

    @Test
    public void streamForEach(){

        ICalled iCalled = new ICalled(){
            @Override
            public List execute() {
                stringList.stream().forEach(s -> {});
                return null;
            }
        };
        iCalled.printTime(Thread.currentThread());
    }

}
