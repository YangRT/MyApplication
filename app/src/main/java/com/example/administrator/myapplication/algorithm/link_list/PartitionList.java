package com.example.administrator.myapplication.algorithm.link_list;

import com.airbnb.lottie.L;

// 给定一个链表和值 x ，把链表中所有比 x 小的节点，排在所有比 x 大的节点的前面。
// 相当于把链表分割成两个部分。
// 一部分是比 x 小的，一部分是比 x 大的。并且在每个部分里面，需要保持链表节点的相对顺序不变
public class PartitionList {

    public ListNode solution(ListNode head,int x){
        ListNode help = new ListNode(0);
        ListNode another = new ListNode(0);
        help.next = head;
        ListNode temp = help;
        ListNode aTemp = another;
        while (help.next != null){
            if (help.next.val >= x){
                aTemp.next = help.next;
                aTemp = aTemp.next;
                aTemp.next = null;
                help.next = help.next.next;
            }else {
                help = help.next;
            }
        }
        help.next = another;
        return temp;
    }
}
