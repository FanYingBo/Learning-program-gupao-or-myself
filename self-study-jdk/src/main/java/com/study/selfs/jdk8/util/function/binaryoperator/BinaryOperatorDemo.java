package com.study.selfs.jdk8.util.function.binaryoperator;

import org.junit.Test;

import java.util.Comparator;
import java.util.function.BinaryOperator;

public class BinaryOperatorDemo {

    @Test
    public void BinaryOperatorTest(){
        BinaryOperator<Integer> binaryOperator = (a,b) -> Math.min(a,b);
        Integer apply = binaryOperator.apply(1, 2);
        System.out.println(apply);
        BinaryOperator<Integer> binaryOperator1 = BinaryOperator.minBy(new CompareTest());
        Integer apply1 = binaryOperator1.apply(1, 2);
        System.out.println(apply1);
        BinaryOperator<Integer> binaryOperator2 = BinaryOperator.minBy(Integer::compare);
        Integer apply2 = binaryOperator2.apply(1, 2);
        System.out.println(apply2);
    }
}
class CompareTest implements Comparator<Integer>{

    public CompareTest() {
    }
    @Override
    public int compare(Integer o1, Integer o2) {
        return o1.compareTo(o2);
    }
}
