package com.study.self.interview.difficulties.leecode;

import java.util.Stack;

/**
 * 使用两个栈构成一个队列
 */
public class CQueue {

    Stack<Integer> stack1;
    Stack<Integer> stack2;

    public CQueue(){
        stack1 = new Stack<>();
        stack2 = new Stack<>();
    }

    public void appendTail(int num){
        stack1.push(num);
    }
    public Integer deleteHead(){
        while(!stack1.empty()){
            stack2.push(stack1.pop());
        }
        Integer pop = stack2.empty() ? -1:stack2.pop();
        while(!stack2.empty()){
            stack1.push(stack2.pop());
        }
        return pop;
    }

    public static void main(String[] args) {
        CQueue cQueue = new CQueue();
        cQueue.appendTail(1);
        System.out.println(cQueue.deleteHead());
        cQueue.appendTail(2);
        cQueue.appendTail(3);
        cQueue.appendTail(4);
        System.out.println(cQueue.deleteHead());
        cQueue.appendTail(5);
        cQueue.appendTail(6);
        cQueue.appendTail(7);
        cQueue.appendTail(8);
        cQueue.appendTail(9);
        cQueue.appendTail(10);
        cQueue.appendTail(11);
        cQueue.appendTail(13);
        cQueue.appendTail(14);
        cQueue.appendTail(15);
        cQueue.appendTail(16);
        System.out.println(cQueue.deleteHead());
        cQueue.appendTail(17);
        cQueue.appendTail(18);
        System.out.println(cQueue.deleteHead());
        System.out.println(cQueue.deleteHead());
        cQueue.appendTail(19);
        cQueue.appendTail(20);
        System.out.println(cQueue.deleteHead());
        System.out.println(cQueue.deleteHead());
        System.out.println(cQueue.deleteHead());
        System.out.println(cQueue.deleteHead());
        System.out.println(cQueue.deleteHead());
        System.out.println(cQueue.deleteHead());

    }
}
