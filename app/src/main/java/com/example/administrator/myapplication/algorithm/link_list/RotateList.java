package com.example.administrator.myapplication.algorithm.link_list;

// 给出一个链表，向右循环链表 k 位，k是一个非负整数
public class RotateList {

    public ListNode solution(ListNode head,int k){
        if (head == null || head.next == null) return head;
        int length = countNodes(head);
        k = k % length;
        if (k == 0) return head;

        //将链表分为两段，前Count-k个节点和后k个节点
        //需要将k个节点，放到Count-k个节点的前面
        ListNode assist = new ListNode(0);
        assist.next = head;
        ListNode cur = assist;
        for(int i = 1 ; i <= length-k ; i++){
            cur = cur.next;
        }
        //现在的cur指向第Count-k个节点，在新链表中它将是最后一个节点，
        //用split将其保存
        ListNode split = cur;
        //在新链表中，第Count-k+1 个节点是第一个节点
        assist.next = cur.next;
        while(cur.next != null){
            cur = cur.next;
        }
        //cur现在指向链表的最后一个节点，在新链表中它将是第k个节点，
        cur.next = head;
        //将新链表的最后一个节点指向null
        split.next = null;
        return assist.next;
    }

    //计算链表总节点数
    public int countNodes(ListNode root){
        int ans = 0;
        while(root != null){
            root = root.next;
            ans++;
        }
        return ans;
    }
}
