package com.example.administrator.myapplication.algorithm.link_list;

// 合并两个有序链表，要求合并后的新链表也是有序的
public class MergeSortedList {

    // 新建链表 不断比较两个链表当前值
    public ListNode solution(ListNode head,ListNode head2){
        ListNode assist = new ListNode();
        ListNode help = assist;
        while (head != null && head2 != null){
            if (head.val < head2.val){
                help.next = head;
                head = head.next;
            }else {
                help.next = head2;
                head2 = head2.next;
            }
            help = help.next;
        }
        if (head != null) help.next = head;
        if (head2 != null) help.next = head2;
        return assist.next;
    }

    // 递归
    public static ListNode solution2(ListNode head,ListNode head2){
        if(head == null) return head2;
        if(head2 == null) return head;
        if (head.val < head2.val){
            head.next = solution2(head.next,head2);
            return head;
        }else {
            head2.next = solution2(head,head2.next);
            return head2;
        }
    }
}
