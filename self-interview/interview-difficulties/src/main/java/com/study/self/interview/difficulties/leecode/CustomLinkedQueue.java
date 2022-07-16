package com.study.self.interview.difficulties.leecode;

import org.junit.Test;

public class CustomLinkedQueue<T> {



    private Node head;
    private Node last;
    private int capacity;
    private int count;

    public CustomLinkedQueue(int capacity) {
        this.capacity = capacity;
        this.head = this.last = new Node(null);
    }


    public void enqueue(T object){
        this.last = this.last.next = new Node(object);
        count ++;
    }

    public T dequeue(){
        if(count == 0){
            return null;
        }
        Node tmp = head;
        head = tmp.next;
        tmp.next = head; // help gc
        T ret = (T) head.getValue();
        head.setValue(null);
        count--;
        return ret;
    }

    public int size() {
        return count;
    }

    private class Node<T>{
        private T value;
        private Node next;

        public Node(T value) {
            this.value = value;
        }

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }
}
