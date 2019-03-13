package com.study.test;

import sun.misc.Contended;

/**
 * 空间换时间
 * Cpu CacheLine伪共享：
 * 多个线程的变量共享了同一个 CacheLine，任意一方的修改操作都会
 * 使得整个 CacheLine 失效（因为 CacheLine 是 CPU 缓存的最小单位），
 * 也就意味着，频繁的多线程操作，CPU 缓存将会彻底失效，降级为 CPU core 和主内存的直接交互。
 *
 * 1.Cpu CacheLine 伪共享测试
 * 2.long占8个字节 64位
 * 3.一个对象头占8个字节，对象头+7个long类型构成一个CacheLine
 * 4.测试（避免伪共享）：
 *       对VolatileLong中的增加6个long类型的填充避免将不同VolatileLong实例对象
 *       和与VolatileLong实例对象无关的变量存在同一个CacheLine造成伪共享
 *
 * Java8中已经提供了官方的解决方案，Java8中新增了一个注解：@sun.misc.Contended。
 * 加上这个注解的类会自动补齐缓存行，需要注意的是此注解默认是无效的，需要在jvm启动时设置-XX:-RestrictContended才会生效。
 */
public class CpuCacheLineDemo {

    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        Thread[] threads = new Thread[TestCpuFalseSharingThread.THREAD_NUMS];
        for(int i = 0;i<threads.length;i++){
            threads[i] = new Thread(new TestCpuFalseSharingThread(i));
            threads[i].start();
        }
        for(Thread thread : threads){
            thread.join();
        }
        long end = System.currentTimeMillis();
        System.out.println("    耗时："+(end-start) +"ms");
    }

}
class TestCpuFalseSharingThread implements Runnable{
    public static int THREAD_NUMS = 4;
    private static long INTERATIONS = 500L*1000L;
    private int arrayIndex;
    private static VolatileLong[] volatileLongs = new VolatileLong[THREAD_NUMS];
    static{
        for(int i=0;i<THREAD_NUMS;i++){
            volatileLongs[i] = new VolatileLong();
        }
    }

    public TestCpuFalseSharingThread(int arrayIndex) {
        this.arrayIndex = arrayIndex;
    }

    @Override
    public void run() {
        long i = INTERATIONS +1;
        while(0 != --i){
            volatileLongs[arrayIndex].value = i;
        }
    }
}

class VolatileLong{
    public volatile long value=1l;
    // CacheLine 填充字节
//    private long p1,p2,p3,p4,p5,p6;
}