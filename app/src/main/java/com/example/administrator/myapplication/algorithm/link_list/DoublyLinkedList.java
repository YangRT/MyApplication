package com.example.administrator.myapplication.algorithm.link_list;

import org.w3c.dom.Node;

// 给出一个双向链表的头节点，这个链表的节点除了 next 指针和 previous指针外，还有一个child指针
// next指针指向下一个节点，previous指针指向前一个节点
// child指针可能指向另外一个双向的链表，也即子链表
// 子链表可以有更多的子链表
// 我们需要做的事情是构建一个多层的数据结构，就像范例中展示的那样
//把所有的链表的节点都放在一个双向链表中。这个双向链表不能有子链表
public class DoublyLinkedList {
    // 把范例里面的链表看作一个树，不难看出答案是以深度优先搜索的方式遍历整个链表
    // 深度搜索 递归
    ListNode dummy = new ListNode();
    ListNode cur = dummy;

    public ListNode flatten(ListNode head) {
        dfs(head);
        return dummy.next;
    }

    public void dfs(ListNode root){
        if (root == null) {
            return;
        }

        cur.next = new ListNode(root.val ,(cur == dummy ? null : cur), null , null);
        cur = cur.next;

        dfs(root.random);
        dfs(root.next);
    }

}
