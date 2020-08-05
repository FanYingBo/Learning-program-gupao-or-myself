package com.study.selfs.leecode;

public class ListNodeCompute {

    public static void main(String[] args) {
        ListNode listNode1 = new ListNode(2);
        listNode1.next = new ListNode(4);
        listNode1.next.next = new ListNode(3);
        ListNode listNode2 = new ListNode(5);
        listNode2.next = new ListNode(6);
        listNode2.next.next = new ListNode(6);
        System.out.println(addTwoNumbers(listNode1,listNode2));
    }
    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode root = new ListNode(-1);
        ListNode cursor = root;
        int overflow = 0;
        while(l1 != null || l2 != null || overflow != 0){
            int l1val = l1 != null ? l1.val:0;
            int l2val = l2 != null ? l2.val:0;
            int sum = l1val + l2val + overflow;
            overflow = sum/10;
            cursor.next = new ListNode(sum % 10);
            cursor = cursor.next;
            if(l1 != null){
                l1 = l1.next;
            }
            if(l2 != null){
                l2 = l2.next;
            }
        }
        return root.next;
    }
}
class ListNode{
    public int val;
    public ListNode next;
    public ListNode(int x){
        this.val = x;
    }

    @Override
    public String toString() {
        return "ListNode{" +
                "val=" + val +
                ", next=" + next +
                '}';
    }
}

