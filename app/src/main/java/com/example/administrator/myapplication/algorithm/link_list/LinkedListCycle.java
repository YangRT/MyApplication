package com.example.administrator.myapplication.algorithm.link_list;

import com.airbnb.lottie.L;

import java.util.HashSet;

// 给定一个链表，判断该链表中是否有环
public class LinkedListCycle {

    // hashSet
    public boolean hasCycle(ListNode head) {
        HashSet<ListNode> set = new HashSet<>();
        ListNode assist = new ListNode(0);
        assist.next = head;

        ListNode cur = assist;
        while(cur.next != null){
            if(set.contains(cur.next) ){
                return true;
            }else{
                set.add(cur.next);
                cur = cur.next;
            }
        }
        return false;
    }

    // 双指针
    public boolean solution(ListNode head){
        if (head == null || head.next == null) return false;
        ListNode fast = head;
        ListNode slow = head;
        while (fast != null && fast.next != null){
            fast = fast.next.next;
            slow = slow.next;
            if (slow == fast) return true;
        }
        return false;
    }
}
