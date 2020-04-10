package com.example.administrator.myapplication.algorithm.link_list;

import java.util.HashSet;
import java.util.Set;

// 给出一个有序链表，删除链表中的重复元素
public class DeleteDuplicateNode {

    // 使用变量保存前一个值
    public ListNode solution(ListNode head){
        if (head == null) return head;
        ListNode assistant = head;
        int before = assistant.val;
        while (assistant.next != null){
            if (assistant.next.val == before){
                assistant.next = assistant.next.next;
            }else {
                assistant = assistant.next;
                before = assistant.val;
            }
        }
        return head;
    }

    // 链表无序时 使用 set
    public ListNode solution2(ListNode head){
        if(head == null) return head;
        ListNode temp = head;
        Set<Integer> set = new HashSet<>();
        set.add(head.val);
        while (head.next != null){
            if (set.contains(head.next.val)){
                head.next = head.next.next;
            }else {
                set.add(head.next.val);
                head = head.next;
            }
        }
        return temp;
    }
}
