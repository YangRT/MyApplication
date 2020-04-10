package com.example.administrator.myapplication.algorithm.link_list;

import com.airbnb.lottie.L;

// 给定一个链表，将链表中的元素两两交换。也就是每遇到2个元素，就交换这两个元素
// 再接着去交换下一对元素。
//不可以只交换链表节点的 val 值，而应该改变链表本身。
public class SwapNode {

    public ListNode solution(ListNode head){
        ListNode help = new ListNode();
        help.next = head;
        ListNode temp = help;
        while (temp.next != null && temp.next.next != null){
            ListNode second = temp.next.next; // 2个元素后一个

            temp.next.next = second.next; // 将前一个的下一个指向后一个的下一个
            second.next = temp.next;      // 将后一个的下一个指向前一个，完成交换
            temp.next = second;           // 将当前的下一个指向原本后一个

            temp = temp.next.next;   // 移动准备交换下一组
        }
        return help.next;
    }

}
