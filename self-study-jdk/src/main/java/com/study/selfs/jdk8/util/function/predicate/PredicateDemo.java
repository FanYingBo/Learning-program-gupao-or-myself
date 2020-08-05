package com.study.selfs.jdk8.util.function.predicate;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *   判断条件
 *  {@link  java.util.function.Predicate}
 */
public class PredicateDemo {

    @Test
    public void testPredicate(){
        List<String> strings = Arrays.asList(new String[]{"tom","jeck","ben","tony"});
        List<String> collect = strings.stream().filter(str -> !str.equals("tom")).collect(Collectors.toList());
        System.out.println(collect);
    }
}
