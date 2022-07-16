package com.study.selfs.jdk5.util.concurrent.cyclicbarrier;


import java.util.concurrent.*;

/**
 * 栅栏
 * 一种闭锁， 能阻塞一组线程直到整个事件发生，栅栏和闭锁的区别在于，所有线程必须达到展览位置才能继续执行
 * 闭锁用于等待事件，栅栏用于等待其他线程
 * {@link CyclicBarrier} 可以使一定数量的参与方反复地在栅栏位置汇集，并行迭代算法中非常有用
 */
public class CyclicBarrierDemo {


    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2, ()->{
            System.out.println("其他线程已执行完毕，合并执行结果");
        });

        executorService.submit(()->{
            try {
                System.out.println("线程：A" +" 走到栅栏前");
                cyclicBarrier.await();
                System.out.println("线程：A" +" 退出");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        executorService.submit(()->{
            try {
                System.out.println("线程：B" +" 走到栅栏前");
                cyclicBarrier.await();
                System.out.println("线程：B" +" 退出");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        executorService.submit(()->{
            try {
                System.out.println("线程：C" +" 走到栅栏前");
                cyclicBarrier.await();
                System.out.println("线程：C"+" 退出");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        executorService.submit(()->{
            try {
                System.out.println("线程：D" +" 走到栅栏前");
                cyclicBarrier.await();
                System.out.println("线程：D" +" 退出");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        executorService.shutdown();
    }
}
