package com.example.administrator.myapplication.algorithm.link_list;

// 写一个函数，删除单向链表中的一个节点
// △只会给出被要求删除的那个节点
// 链表中至少有2个元素
//所有链表节点的值，都是互不相同的
//给出的节点，不会是链表的尾节点，并且一定是链表中一个有效的节点。
//函数不需要返回任何值
public class DeleteNode2 {

    // 无法知道前一节点 所以无法将前一节点直接指向后一节点
    // 所有链表节点的值，都是互不相同的，将本节点值改为后一节点的值
    // 再跳过后一节点
    public void solution(ListNode node){
        node.val = node.next.val;
        node.next = node.next.next;
    }
}
