package com.study.self.interview.difficulties.leecode;

import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @param <T>
 */
public class CustomArrayQueue<T> {

    private Object[] tables;

    private int putIndex = 0;
    private int takeIndex = 0;
    private int count = 0;

    private Lock lock = new ReentrantLock();

    private Condition notFull;
    private Condition notEmpty;

    public CustomArrayQueue(int capacity){
        tables = new Object[capacity];
        notFull = lock.newCondition();
        notEmpty = lock.newCondition();
    }

    public void enqueue(T object){
        try{
            lock.lock();
            if(count == this.tables.length){
                notFull.await();
            }
            this.tables[putIndex] = object;
            if(++putIndex == this.tables.length){
                putIndex = 0;
            }
            count++;
            notEmpty.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    public T dequeue() {
        try {
            lock.lock();
            if (count == 0) {
                notEmpty.await();
            }
            Object ret = this.tables[takeIndex];
            if (++takeIndex == this.tables.length) {
                takeIndex = 0;
            }
            count--;
            notFull.signal();
            return (T) ret;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        } finally {
            lock.unlock();
        }
    }


    @Override
    public String toString() {
        return "CustomArrayQueue{" +
                "tables=" + Arrays.toString(tables) +
                ", putIndex=" + putIndex +
                ", takeIndex=" + takeIndex +
                ", count=" + count +
                '}';
    }
}
