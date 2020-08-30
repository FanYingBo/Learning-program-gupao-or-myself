package com.study.selfs.jvm;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 死锁分析
 * 死锁，与死锁检查
 * 分析：死锁的过程发生了什么？
 */
public class DeadLockDemo {

    private static Object lock1 = new Object();
    private static Object lock2 = new Object();
    private static ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
    private static CountDownLatch countDownLatch = new CountDownLatch(2);

    public static void main(String[] args) {
        deadLock();
        checkDeadLock();
        dumpThreadInfo();
    }

    /**
     * 1.通过ManagementFactory.getThreadMXBean() 获取当前的线程MXBean
     * 2.根据线程ID和锁等待的对象，查看线程死锁信息
     *
     */
    private static void checkDeadLock(){
        Thread checkThread = new Thread(()->{
            while(true){
                long[] deadlockedThreads = threadMXBean.findDeadlockedThreads();
                if(deadlockedThreads != null){
                    for(long threadId : deadlockedThreads){
                        ThreadInfo threadInfo = threadMXBean.getThreadInfo(threadId);
                        // 这里是获取现在阻塞时候，锁等待的对象
                        System.out.println("当前线程ID："+threadInfo.getThreadId()+" lock:"+threadInfo.getLockInfo()+"，锁的拥有者："+threadInfo.getLockOwnerId());
                    }
                }
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        checkThread.start();

    }

    /**
     * 线程如果出现{@link IllegalMonitorStateException} 当前锁未被当前线程拥有，证明当前无法操作对象锁等待或者释放对象锁的状态
     */
    private static void deadLock(){
        Thread thread = new Thread(()->{
            synchronized (lock1){
                countDownLatch.countDown();
                System.out.printf("[%d] 拥有的锁 "+lock1+"\n",Thread.currentThread().getId());
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lock2){
                }
            }
        });
        Thread thread1 = new Thread(()->{
            synchronized (lock2){
                countDownLatch.countDown();
                System.out.printf("[%d] 拥有的锁 "+lock2+"\n",Thread.currentThread().getId());
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lock1){
                }
            }
        });
        thread.start();
        thread1.start();
    }

    /**
     * 打印线程堆栈信息
     * 如果线程池出现异常可以使用这种方式打印线程堆栈信息
     */
    private static void dumpThreadInfo(){
        new Thread(()->{
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 这里可以获取当前线程的占用CPU的时间
            long currentThreadCpuTime = threadMXBean.getCurrentThreadCpuTime();
            // 用户时间（用户处理一些IO数据的时间）
            long currentThreadUserTime = threadMXBean.getCurrentThreadUserTime();
            System.out.println("currentThreadCpuTime: "+ currentThreadCpuTime+" currentThreadUserTime:"+currentThreadUserTime);

            ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(true, true);
            for(ThreadInfo threadInfo : threadInfos){
                // 线程ID
                long threadId = threadInfo.getThreadId();
                // 线程状态
                Thread.State threadState = threadInfo.getThreadState();

            }
        }).start();
    }
}
