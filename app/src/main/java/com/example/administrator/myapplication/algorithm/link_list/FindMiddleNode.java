package com.example.administrator.myapplication.algorithm.link_list;

// 给定一个非空单向链表，返回这个链表的中点。
// 如果这个链表的节点数为偶数，就返回最中间的2个节点中后面的那个
public class FindMiddleNode {

    // 双指针 ： 使用快，慢指针，快指针2步，慢指针1步
    // 当快指针为空时，慢指针刚好到中点
    public ListNode solution(ListNode head){
        ListNode fast = head;
        ListNode slow = head;
        while (fast != null && fast.next != null){
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }
}
