package com.study.selfs.jdk5.util.concurrent.cyclicbarrier;

import java.util.Objects;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierDoubleCount implements Runnable {

    private int offset;
    private int added;
    private CyclicBarrier barrier;

    public CyclicBarrierDoubleCount(int offset, int added) {
        this.offset = offset;
        this.added = added;
    }

    public CyclicBarrierDoubleCount(int offset, int added, CyclicBarrier cyclicBarrier) {
        this.offset = offset;
        this.added = added;
        this.barrier = cyclicBarrier;
    }

    public CyclicBarrier getBarrier() {
        return barrier;
    }

    public void setBarrier(CyclicBarrier barrier) {
        this.barrier = barrier;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    @Override
    public void run() {
        if(Objects.nonNull(barrier)){
            try {
                offset += added;
                System.out.println("第一步，线程："+Thread.currentThread().getName()+" 结果："+offset);
                barrier.await();
                offset += added;
                System.out.println("第二步，线程："+Thread.currentThread().getName()+" 结果："+offset);
                barrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }
}
