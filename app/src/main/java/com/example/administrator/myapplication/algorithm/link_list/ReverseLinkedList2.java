package com.example.administrator.myapplication.algorithm.link_list;

// 给出一个链表，以及数字 m、n。
// 反转 [m , n ] 部分的链表。使用一次循环
public class ReverseLinkedList2 {

     // 一个指针pre ，指向第 m-1 个节点；一个指针 cur ，指向第 m 个节点。
    //思路:依次把 cur 后面的节点放到第 m-1 个节点的后面
    public ListNode solution(ListNode head,int m,int n){
        if(head == null || m == n || head.next == null){
            return head;
        }
        ListNode assistant = new ListNode();
        assistant.next = head;
        ListNode pre = assistant;
        for (int i=1;i < m;i++){
            pre = pre.next;
        }
        ListNode cur = pre.next;
        for(int i =1;i <= n-m;i++){
            ListNode temp = cur.next;
            cur.next = cur.next.next;
            pre.next = temp;
            temp.next = pre.next;
        }
        return assistant.next;
    }

}
