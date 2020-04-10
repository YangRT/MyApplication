package com.example.administrator.myapplication.algorithm.link_list;

import java.util.Stack;

public class ReverseLinkedList {

    public ListNode solution(ListNode head){
        if (head == null || head.next == null) return head;
        ListNode per = null;
        ListNode cur = head;
        ListNode next;
        while (cur != null){
            next = cur.next;
            cur.next = per;
            per = cur;
            cur = next;
        }
        return per;
    }

    // 递归
   public ListNode solution2(ListNode node){
        if (node == null || node.next==null) return node;
        ListNode head = solution2(node.next);
        node.next.next = node;
        node.next = null;
        return head;
   }


}
