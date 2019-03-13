package com.study.jdk5.util.concurrent.priorityblockingqueue;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Stack;
import java.util.concurrent.PriorityBlockingQueue;


/**
 *
 * @since 1.5
 * @see java.util.concurrent.locks.ReentrantLock
 * @see java.util.concurrent.locks.Condition
 * @see java.util.concurrent.locks.AbstractQueuedSynchronizer
 * @see PriorityBlockingQueue
 * 笔试题：获得一个数组的最小的key个数  基于PriorityBlockingQueue实现
 *  PriorityBlockingQueue基于数组存储，基于比较器的存储方式。
 *  队列的第一个值永远是最小过着最大的那个值，后面的数据不确定
 */
public class PriorityBlockingQueueDemo {

    public static void main(String[] args) {
//        int[] input = new int[]{4,5,10,1,6,7,9,8,11,20,40,3};
        int[] input = {8,2,3,12,5,7,1,1,4,9};
        int[] lower = getLowerOrHigher(input, 5,false); // true 获得最小序列，false 获得最大序列
        System.out.println(Arrays.toString(lower));

    }

    /**
     * 从一个数组中取出最小的（key）个值
     * @param input
     * @param key
     * @return
     */
    public static int[] getLowerOrHigher(int[] input, int key ,Boolean ltOrHt){
        int length = input.length;
        if(key > length || key < 1){
            return input;
        }
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>(key, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return ltOrHt ? Integer.compare(o2,o1):Integer.compare(o1,o2);
            }
        });
        for(int i = 0;i < length;i++){
            queue.offer(input[i]);
            if(queue.size() > key){
                Integer poll = queue.poll();
                System.out.println("poll:  "+poll+" queue: "+queue);
            }
        }
        int[] result = new int[key];
        for(int i = key-1;i >= 0 ;i--){
            Integer poll = queue.poll();
            result[i] = poll;
        }
        return result;
    }

}
