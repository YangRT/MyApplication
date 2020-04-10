package com.example.administrator.myapplication.algorithm.link_list;

// 判断一个链表是不是回文串
public class Palindrome {

    public boolean isPalindrome(ListNode head) {
        if( head == null || head.next == null){
            return true;
        }
        //将链表从中间一分为二,并且统计链表的长度
        ListNode fast = head;
        ListNode slow = head;
        ListNode slowPre = null;
        int length = 0;
        while(fast != null && fast.next != null){
            fast = fast.next.next;
            slowPre = slow;
            slow = slow.next;
            length += 2;
        }
        slowPre.next = null;
        if(fast != null){
            length += 1;
        }

        //将后半部分链表反转
        //如果长度为奇数，则将链表中点之后的部分反转，不包括中点
        ListNode before = null;
        ListNode next = null;
        ListNode l = (length % 2 == 0 ?  slow : slow.next);

        while(l != null){
            next = l.next;
            l.next = before;
            before = l;
            l = next;
        }
        //反转后的链表的头结点是before所指向的节点

        //比较前半部分链表和经过反转的后半部分链表是否相同
        ListNode l1 = head;
        ListNode l2 = before;

        while(l1 != null ){
            if(l1.val == l2.val){
                l1 = l1.next;
                l2 = l2.next;
            }else{
                return false;
            }
        }
        return true;
    }

}
