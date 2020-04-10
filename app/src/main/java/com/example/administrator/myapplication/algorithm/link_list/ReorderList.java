package com.example.administrator.myapplication.algorithm.link_list;

// 给出一个单项链表，L0→L1→…→Ln-1→Ln ，需要将其重新排列顺序
// 变成： L0→Ln→L1→Ln-1→L2→Ln-2→…
//你不可以改变链表的节点的val，只能改变他们的连接关系。
public class ReorderList {

    public void reorderList(ListNode head) {

        if(head == null || head.next == null){
            return;
        }

        //第一步：利用快慢指针法，将链表从中间一分为二
        ListNode fast = head;
        ListNode slow = head;
        ListNode slowPre = null;
        while(fast != null && fast.next != null){
            fast = fast.next.next;
            slowPre = slow;
            slow = slow.next;
        }
        slowPre.next = null;
        //一分为二后，链表的两段，前一段从head开始，到slowPre结束
        //后一段从slow开始，到链表尾部结束


        //第二步，反转后一段链表，也就是从slow开始的那段链表
        //反转的方法是让每一个节点的next都指向它前面的节点
        ListNode cur = slow;
        ListNode before = null;
        while(cur != null){
            ListNode behind = cur.next;
            cur.next = before;
            before = cur;
            cur = behind;
        }
        //反转后的链表，第一个节点是before所指的节点


        //第三步,合并两个链表
        ListNode l1 = head;
        ListNode l2 = before;
        ListNode assist = new ListNode(0);
        while(l1 != null || l1 != null){
            if(l1 != null){
                assist.next = l1;
                l1 = l1.next;
                assist = assist.next;
            }

            if(l2 != null){
                assist.next = l2;
                l2 = l2.next;
                assist = assist.next;
            }
        }

    }

}
