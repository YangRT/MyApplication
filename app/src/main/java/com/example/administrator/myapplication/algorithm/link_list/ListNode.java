package com.example.administrator.myapplication.algorithm.link_list;

public class ListNode {

    public int val;
    public ListNode prev;
    public ListNode next;
    public ListNode random;

    public ListNode(int val){
        this.val = val;
        next = null;
    }

    public ListNode(){

    }

    public ListNode(int val,ListNode next,ListNode random){
        this.next = next;
        this.random = random;
        this.val = val;
    }

    public ListNode(int val,ListNode prev,ListNode next,ListNode random){
        this.next = next;
        this.random = random;
        this.val = val;
        this.prev = prev;
    }
}
