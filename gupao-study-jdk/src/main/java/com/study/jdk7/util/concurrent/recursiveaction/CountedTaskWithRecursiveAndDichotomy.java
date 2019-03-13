package com.study.jdk7.util.concurrent.recursiveaction;



import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.atomic.LongAdder;

/**
 *
 * 并行计算
 * 通过递归和二分法计算一连串数的和
 * @since 1.7
 * @see java.util.concurrent.ForkJoinPool
 * @see java.util.concurrent.ForkJoinTask
 * @see java.util.concurrent.RecursiveAction  递归求值任务
 */
public class CountedTaskWithRecursiveAndDichotomy extends RecursiveAction {


    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();

        List<Integer> integers = Arrays.asList(13, 25, 31, 56, 5, 35, 87, 8, 2, 21);

        LongAdder longAdder = new LongAdder();

        CountedTaskWithRecursiveAndDichotomy addTask = new CountedTaskWithRecursiveAndDichotomy(integers,longAdder);

        forkJoinPool.invoke(addTask);

        forkJoinPool.isShutdown();

        System.out.println("累加的结果："+longAdder);
    }




    private List<Integer> comList;

    private LongAdder addedCount;

    public CountedTaskWithRecursiveAndDichotomy(List<Integer> comList, LongAdder addedCount) {
        this.comList = comList;
        this.addedCount = addedCount;
    }
    @Override
    public void compute() {
        int size = comList.size();

        if(size > 1){
            // 二分法计算
            int parts = size / 2;

            List<Integer> leftParts = comList.subList(0, parts);
            CountedTaskWithRecursiveAndDichotomy addTaskLeft = new CountedTaskWithRecursiveAndDichotomy(leftParts,addedCount);

            List<Integer> rightParts = comList.subList(parts, size);
            CountedTaskWithRecursiveAndDichotomy addTaskRight = new CountedTaskWithRecursiveAndDichotomy(rightParts,addedCount);

            invokeAll(addTaskLeft,addTaskRight);
        }else{
            if(size == 0){
                return;
            }
            // size为 1 直接加
            Integer integer = comList.get(0);

            addedCount.add(integer);
        }

    }


}
