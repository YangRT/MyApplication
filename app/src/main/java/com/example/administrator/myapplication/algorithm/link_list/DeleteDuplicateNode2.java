package com.example.administrator.myapplication.algorithm.link_list;

// 给定一个有序链表，删除所有出现次数 1 次以上的元素
// 只保留出现次数刚好 1 次的元素。
public class DeleteDuplicateNode2 {

    // 双指针
    public ListNode solution(ListNode head){
        if (head == null || head.next == null) return head;
        ListNode node = new ListNode();
        node.next = head;
        ListNode left = node;
        ListNode right = node.next.next;
        while (right != null){
            if (left.next.val == right.val){
                while(right != null && right.val == left.next.val){
                    right = right.next;
                }
                left.next = right;
                right = (right == null ? null : right.next);
            }else {
                left = left.next;
                right = right.next;
            }
        }
        return node.next;
    }

}
