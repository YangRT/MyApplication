package com.example.administrator.myapplication.algorithm.link_list;

import org.w3c.dom.Node;

// 给定一个链表，这个链表的每个节点都包含了一个额外的随机指针
// 这个随机指针可以指向任意节点或者null
//返回一个链表的深拷贝
// 即重新在堆内存中做一个和原有链表一模一样的链表，
// 两者只是内容相同。修改其中一个链表，不会改变另外的链表
public class CopyList {

    //深拷贝: 源对象与拷贝对象互相独立
    //其中任何一个对象的改动都不会对另外一个对象造成影响
    public ListNode solution(ListNode head){
        if (head == null) {
            return null;
        }
        //让每个节点后面有一个和自己一样的复制节点
        ListNode cur = head;
        while(cur != null){
            cur.next = new ListNode(cur.val , cur.next , cur.random);
            cur = cur.next.next;
        }

        //让复制节点的 random 指向原来 random 的复制节点
        cur = head;
        while(cur != null ){
            cur.next.random = ( cur.random == null ? null : cur.random.next);
            cur = cur.next.next;
        }

        //恢复原来的链表，分离复制节点组成的深拷贝
        cur = head;
        ListNode res = head.next;
        while(cur != null){
            ListNode temp = cur.next;
            cur.next = temp.next;
            if (temp.next != null) {
                temp.next = temp.next.next;
            }
            cur =cur.next;
        }
        return res;
    }
}
