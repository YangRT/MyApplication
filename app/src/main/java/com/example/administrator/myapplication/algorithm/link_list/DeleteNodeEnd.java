package com.example.administrator.myapplication.algorithm.link_list;

// 给定一个链表，删除倒数第 n 个元素，然后返回新链表的头指针
public class DeleteNodeEnd {

    // 遍历获取长度 再次遍历删除
    public ListNode solution(int n,ListNode head){
        ListNode help = new ListNode(0);
        help.next = head;
        int size =  0;
        ListNode current = help;
        while (current.next != null){
            size++;
            current = current.next;
        }
        current = help;
        int length = 1;
        while (current.next != null){
            if (length ==size - n){
                current.next = current.next.next;
                break;
            }
            length++;
            current = current.next;
        }
        return help.next;
    }

    // 双指针 设置左，右指针，左指针从第0个开始 右指针从第n+1个开始
    // 当右指针为空时 左指针的下一个就是倒数第n个
    public ListNode solution2(int n,ListNode head){
        ListNode help = new ListNode(0);
        help.next = head;
        ListNode left = help;
        ListNode right = help;
        for(int i = 0;i <= n;i++){
            right = right.next;
        }
        while (right != null){
            left = left.next;
            right = right.next;
        }
        left.next = left.next.next;
        return help.next;
    }
}
