package com.study.selfs.jdk5.util.concurrent.cyclicbarrier;


import java.util.concurrent.*;

/**
 * 栅栏
 * 并行迭代
 */
public class CyclicBarrierDemoCount {

    public static void main(String[] args) {
        CyclicBarrierDoubleCount cyclicBarrierCount = new CyclicBarrierDoubleCount(1, 2);
        CyclicBarrierDoubleCount cyclicBarrierCount2 = new CyclicBarrierDoubleCount(3, 2);
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2, ()->{
            int i = cyclicBarrierCount.getOffset() + cyclicBarrierCount2.getOffset();
            System.out.println("其他线程已执行完毕，合并执行结果:"+i);
        });
        cyclicBarrierCount.setBarrier(cyclicBarrier);
        cyclicBarrierCount2.setBarrier(cyclicBarrier);
        new Thread(cyclicBarrierCount).start();
        new Thread(cyclicBarrierCount2).start();
    }

    private static void countDemo(){
        CyclicBarrierCount cyclicBarrierCount = new CyclicBarrierCount(1, 2);
        CyclicBarrierCount cyclicBarrierCount2 = new CyclicBarrierCount(3, 2);
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2, ()->{
            int i = cyclicBarrierCount.getOffset() + cyclicBarrierCount2.getOffset();
            System.out.println("其他线程已执行完毕，合并执行结果:"+i);
        });
        cyclicBarrierCount.setBarrier(cyclicBarrier);
        cyclicBarrierCount2.setBarrier(cyclicBarrier);
        new Thread(cyclicBarrierCount).start();
        new Thread(cyclicBarrierCount2).start();
    }
}
