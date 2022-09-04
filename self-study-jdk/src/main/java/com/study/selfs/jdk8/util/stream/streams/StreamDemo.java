package com.study.selfs.jdk8.util.stream.streams;

import com.study.selfs.test.util.ListUtils;
import com.study.selfs.timercall.ICalled;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 *  1.遍历对比 {@link #traversePerformance()}
 *  2.reduce {@link #reducePerformance}当数据量超过一定的临界值（测试的list集合 超过2000000的大小时） 使用
 *      {@link Stream#parallel()} or {@link List#parallelStream()}
 */
public class StreamDemo {
    public List<Integer> intList ;

    public List<String> stringList ;

    public String testString = "nice to meet you stream and stream make programmer easy";
    public List<Integer> fixed = new ArrayList<>();
    public List<ExampleStatistic> exampleStatistics = new ArrayList<>();


    @Before
    public void initList(){
        intList = ListUtils.getRanIntList(30000000,1000);
        stringList = ListUtils.getRanStr(1000000,3);
        fixed = Arrays.asList(new Integer[]{1,2,4,5});
        exampleStatistics.add(new ExampleStatistic( 1, BigDecimal.valueOf(2), BigDecimal.valueOf(5.88)));
        exampleStatistics.add(new ExampleStatistic( 1, BigDecimal.valueOf(3), BigDecimal.valueOf(10.88)));
        exampleStatistics.add(new ExampleStatistic( 1, BigDecimal.valueOf(4), BigDecimal.valueOf(15.88)));
    }
    @Test
    public void parallelStreamSort(){
        long start = System.currentTimeMillis();
        intList.parallelStream().sorted(Integer::compare).collect(Collectors.toList());
        System.out.println("Collections Sort 耗时："+(System.currentTimeMillis()-start));// 较 变慢5倍
        System.out.println(intList);
    }

    /**
     * difference between flatMap and Map
     */
    @Test
    public void flatMapOrMap(){
        List<String> list = new ArrayList<>();
        list.add(testString);
        //flatMap方法 返回的是stream
        list.stream().flatMap(t -> Stream.of(t.split("\\s+")).map(s-> s+"1")).forEach(System.out::println);
        //Map方法 返回的是object
        list.stream().map(t -> Stream.of(t.split("\\s+")).map(s ->s+"1")).forEach(stringStream -> System.out.println(Arrays.toString(stringStream.toArray())));
    }

    @Test
    public void groupCountAggregationOP(){
//        IntStream.of(1, 2, 3, 6, 8).boxed().collect(Collectors.toCollection(LinkedList::new));
        //计数
        Map<String, Long> longMap = Stream.of(testString.split("\\s+"))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        System.out.println(longMap); //{meet=1, stream=2, and=1, programmer=1, to=1, easy=1, make=1, you=1, nice=1}
        // 最小值
        System.out.println(fixed.stream().collect(Collectors.minBy(Integer::compareTo)).get()); // 1
        // 最大值
        System.out.println(fixed.stream().collect(Collectors.maxBy(Integer::compareTo)).get()); // 5
        // 平均值
        System.out.println(fixed.stream().collect(Collectors.averagingInt(Integer::intValue)).doubleValue()); // 3.0
        // 统计方式
        System.out.println(fixed.stream().collect(Collectors.summarizingInt(Integer::intValue)));
        System.out.println(fixed.stream().collect(Collectors.summarizingInt(Integer::intValue)).getMax()); // 5
        System.out.println(fixed.stream().collect(Collectors.summarizingInt(Integer::intValue)).getMin()); // 1



//        Map<BigDecimal, BigDecimal> statisticMap = exampleStatistics.stream()
//                .collect(Collectors.toMap(ExampleStatistic::getLotNumber, exampleStatistic -> exampleStatistic.getExecuteValue()));


    }
    @Test
    public void reduceTest(){
        // 最大值
        System.out.println(fixed.stream().reduce(Integer::max).get());
        // 求和
        System.out.println(fixed.stream().reduce(0, (a,b)->a+b));
        // 对象
        System.out.println(exampleStatistics.stream().map(ExampleStatistic::getLotNumber).reduce(BigDecimal::add).get());
        // 对象
        System.out.println(sumField(exampleStatistics, ExampleStatistic::getLotNumber));
    }

    private <T> BigDecimal sumField(List<T> data, Function<T, BigDecimal> function){
        return data.stream().map(function).reduce(BigDecimal.ZERO, BigDecimal::add);
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
