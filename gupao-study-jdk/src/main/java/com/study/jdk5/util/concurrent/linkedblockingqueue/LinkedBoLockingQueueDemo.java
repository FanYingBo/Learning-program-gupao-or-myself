package com.study.jdk5.util.concurrent.linkedblockingqueue;

import com.study.test.util.PrintUtils;
import com.study.util.thread.PutThread;
import com.study.util.thread.TakeThread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 阻塞队列
 * @since 1.5
 * @see java.util.concurrent.LinkedBlockingQueue
 * @see java.util.concurrent.locks.ReentrantLock
 * @see java.util.concurrent.locks.Condition
 * @see java.util.concurrent.atomic.AtomicInteger
 * 1.基于RetrantLock的非公平锁 链表+自旋+CAS(AQS) （LockSupport.unpark(thread)）
 * 2.基于Condition的await() , single()和singleAll()
 * 3.基于链表
 * 4.基于AtomicInteger
 * 5.先进先出 FIFO
 */
public class LinkedBoLockingQueueDemo {

    public static void main(String[] args) throws InterruptedException {
        LinkedBlockingQueue<String> linkedBlockingQueue = new LinkedBlockingQueue<>(10);
        CountDownLatch countDownLatch = new CountDownLatch(2);
//        baseComprehend(linkedBlockingQueue);
        // 默认队列容量大小为 int最大值
        PutThread putThread = new PutThread(linkedBlockingQueue, countDownLatch, 4);// 取出数据线程
        TakeThread takeThread = new TakeThread(linkedBlockingQueue,countDownLatch,4);// 取出数据线程
        putThread.start();
        takeThread.start();
        countDownLatch.await();
    }


    private static void baseComprehend(LinkedBlockingQueue<String> linkedBlockingQueue) throws InterruptedException {
        // 放入队列也可通过add 方法添加到队列中 (同offer)
        // 添加步骤：
        // 1.判断当前队列大小是否超过最大值
        // 2.定义一个新的 Node，放入待添加的值
        // 3.获得一个putLock的重入锁ReentrantLock
        // 4.当前队列小于容量最大值则添加到队列中 enqueue(){last = last.next = node;}
        // 5.队列大小加 1，Condition 唤醒
        // 5.释放putLock的重入锁ReentrantLock
        /***
         * offer
         * 不会阻塞当前线程,即超过队列容量大小的增加会被丢弃
         *    final ReentrantLock putLock = this.putLock;
         *         putLock.lock();
         *         try {
         *             if (count.get() < capacity) {
         *                 enqueue(node);
         *                 c = count.getAndIncrement();
         *                 if (c + 1 < capacity)
         *                     notFull.signal();
         *             }
         *         } finally {
         *             putLock.unlock();
         *         }
         *
         *
         * put
         * 阻塞所有的put线程，待容量空出后放入队列，元素不会被丢弃
         *   putLock.lockInterruptibly();
         *         try {
         *
         *              * Note that count is used in wait guard even though it is
         *              * not protected by lock. This works because count can
         *              * only decrease at this point (all other puts are shut
         *              * out by lock), and we (or some other waiting put) are
         *              * signalled if it ever changes from capacity. Similarly
         *              * for all other uses of count in other wait guards.
         *             while (count.get() == capacity) {
         *                 notFull.await();
         *             }
         *             enqueue(node);
         *             c = count.getAndIncrement();
         *             if (c + 1 < capacity)
         *                 notFull.signal();
         *              } finally {
         *             putLock.unlock();
         *              }
         *
         *
         *
         */
        linkedBlockingQueue.offer("21212");
        linkedBlockingQueue.put("sdadas");
        Thread thread = new Thread(() -> {
            try {
                linkedBlockingQueue.put("sdadas" + 1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
        thread.join();
        PrintUtils.print("取出不移除："+linkedBlockingQueue.peek());// 取出第一个但不移除
        PrintUtils.print(linkedBlockingQueue);
        PrintUtils.print("取出并移除："+linkedBlockingQueue.poll());// 取出第一个并且移除
        PrintUtils.print(linkedBlockingQueue);
        linkedBlockingQueue.take();// 队列size为零会阻塞
    }

}
