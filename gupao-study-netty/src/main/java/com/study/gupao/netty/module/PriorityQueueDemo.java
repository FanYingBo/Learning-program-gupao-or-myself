package com.study.gupao.netty.module;

import io.netty.util.internal.DefaultPriorityQueue;
import io.netty.util.internal.PriorityQueue;
import io.netty.util.internal.PriorityQueueNode;

import java.util.Comparator;

/**
 * {@link io.netty.util.internal.PriorityQueue}
 */
public class PriorityQueueDemo {

    public static void main(String[] args) {
        PriorityQueue<PriorityNode> priorityQueue = new DefaultPriorityQueue(new PriorityNode(1),16);
        priorityQueue.add(new PriorityNode(3));
        priorityQueue.add(new PriorityNode(10));
        priorityQueue.add(new PriorityNode(4));
        priorityQueue.add(new PriorityNode(13));
        priorityQueue.add(new PriorityNode(9));
        priorityQueue.add(new PriorityNode(18));
        System.out.println(priorityQueue);
        for(; priorityQueue.size() > 0;){
            System.out.println(priorityQueue.poll());
            System.out.println(priorityQueue);
        }

    }

    private static class PriorityNode implements PriorityQueueNode , Comparator<PriorityNode> {

        private int priorityIndex = INDEX_NOT_IN_QUEUE;
        private Integer value;

        private PriorityNode(Integer value) {
            this.value = value;
        }

        @Override
        public int priorityQueueIndex(DefaultPriorityQueue<?> queue) {
            return priorityIndex;
        }

        @Override
        public void priorityQueueIndex(DefaultPriorityQueue<?> queue, int i) {
            this.priorityIndex = i;
        }

        @Override
        public int compare(PriorityNode o1, PriorityNode o2) {
            return Integer.compare(o1.value,o2.value);
        }

        @Override
        public String toString() {
            return "PriorityNode{" +
                    "priorityIndex=" + priorityIndex +
                    ", value=" + value +
                    '}';
        }
    }
}
