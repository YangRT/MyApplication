package com.example.administrator.myapplication.algorithm.link_list;

// 给出一个链表，链表每k个节点，都要反转一次
// k是一个正整数，它小于等于链表的长度。
// 如果链表的长度不是 k 的整数倍，那么链表最后剩余的小于k的部分顺序不变
public class ReverseNodes {

    public ListNode solution(int k,ListNode head){
        if (head == null || k <= 1) return head;
        ListNode assistant = new ListNode();
        assistant.next = head;
        int len = 0;
        ListNode pre = assistant;
        ListNode cur;
        ListNode temp = assistant.next;
        // 计算长度
        while (temp != null){
            len++;
            temp = temp.next;
        }

        while (len>=k){
            cur = pre.next;
            temp = cur.next;
            // 反转 k 个节点
            for(int i = 1;i <k;i++){
                cur.next = temp.next;
                temp.next = pre.next;
                pre.next = temp;
                temp = cur.next;
            }
            len -= k;
            pre = cur;
        }
        return assistant.next;
    }

}
