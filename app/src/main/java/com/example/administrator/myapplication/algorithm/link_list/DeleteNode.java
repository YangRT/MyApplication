package com.example.administrator.myapplication.algorithm.link_list;

// 删除链表中所有元素值为 val 的节点
public class DeleteNode {

    public ListNode solution(ListNode head,int val){
        // 设置辅助节点
        ListNode help = new ListNode(0);
        help.next = head;
        ListNode current = help;
        while (current.next != null){
            if (current.next.val == val){
                current.next = current.next.next;
            }else {
                current = current.next;
            }
        }
        return help.next;
    }
}
