package com.example.administrator.myapplication.algorithm.link_list;

import java.util.HashSet;

// 给出一个链表，返回环形开始的那个节点
// 如果这个链表中没有环，那么就返回null
public class LinkedListCycle2 {

    // HashSet
    public ListNode detectCycle(ListNode head) {
        if (head ==null || head.next ==null) {
            return null;
        }

        ListNode assist = new ListNode(0);
        assist.next = head;
        HashSet<ListNode> set = new HashSet<>();
        ListNode cur = assist.next;

        while(cur != null){
            if (set.contains(cur) == true) {
                return cur;
            }else{
                set.add(cur );
                cur = cur.next;
            }
        }
        return null;
    }

    // 双指针
    // 慢指针一开始到二者第一次相遇，之间的路程就等于环的长度
    // 找到环后从起点和相遇点一起走，每次走一步，那么正好会在环起始点相遇
    public ListNode solution(ListNode head) {

        if (head ==null || head.next ==null) {
            return null;
        }

        ListNode assist = new ListNode(0);
        assist.next = head;

        ListNode slow = assist.next;
        ListNode fast = assist.next;

        //这里必须用do-while循环
        //两个指针的起始位置都是一样的，要先跑起来，再判断是否相等
        do{
            if( fast == null || fast.next == null){
                return null;
            }
            fast = fast.next.next;
            slow = slow.next;
        }
        while(fast != slow);

        //慢指针回到head处
        slow = assist.next;
        while(slow != fast){
            slow = slow.next;
            fast = fast.next;
        }
        return slow;
    }


}
