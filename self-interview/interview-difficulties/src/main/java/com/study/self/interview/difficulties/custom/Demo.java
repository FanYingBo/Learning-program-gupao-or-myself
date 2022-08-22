package com.study.self.interview.difficulties.custom;

import com.study.self.interview.difficulties.leecode.CustomLinkedQueue;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Demo {


    @Test
    public void testCustomerQueue(){
        CustomLinkedQueue customLinkedQueue = new CustomLinkedQueue(10);
        customLinkedQueue.enqueue(1);
        System.out.println(customLinkedQueue.dequeue());
        customLinkedQueue.enqueue(2);
        System.out.println(customLinkedQueue.dequeue());
        System.out.println(customLinkedQueue.size());
    }


    public static boolean solution2(int[] A){
        List<Integer> collect = Arrays.stream(A).boxed().collect(Collectors.toList());
        Map<Integer, Long> collect1 = collect.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        long count = collect1.entrySet().stream().filter(integerLongEntry -> integerLongEntry.getValue() != 2).count();
        return count == 0;
    }

    public static void solution3(int N){
        long start = System.currentTimeMillis();
        String str = String.valueOf(N);
        StringBuilder reverse = new StringBuilder(str).reverse();
        System.out.println(Long.valueOf(reverse.toString().trim()));
        System.out.println("end:"+ (System.currentTimeMillis() - start));
    }
    public static void solution(int N) {
        int ret = 0;
        while(N > 0){
            int num = N % 10;
            ret = ret * 10 + num;
            N = N/10;
        }
        System.out.println(ret);
    }
}
