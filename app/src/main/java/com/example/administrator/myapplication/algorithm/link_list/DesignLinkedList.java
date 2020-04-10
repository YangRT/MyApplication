package com.example.administrator.myapplication.algorithm.link_list;

//设计链表类，由它来负责链表的增、删、改等操作
// 假设所有的节点的标号都是从0开始的
// 下面是链表类需要实现的方法：
//get(index) :
//得到第index个节点的val值，如果index不存在，返回-1
//addAtHead(val) :
//在链表头部插入一个新节点，新增的节点成为链表新的头节点
//addAtTail(val) :
//在链表的尾部插入一个新节点
//addAtIndex(index, val) :
//在第 index 个节点前插入一个新节点。如果 index 刚好是链表的长度，
// 那么这个插入的新节点会成为链表的尾节点。如果index 大于链表的长度，这个节点不会被插入。
//deleteAtIndex(index) :
//如果有第 index 个节点，那么删除第 index 个节点。

public class DesignLinkedList {

    // 如果是单向链表，链表节点需要有val 和 next 两个属性。val是节点的值，next是下一个节点
    private ListNode head;
    private int length;

    public DesignLinkedList(){
        head = null;
        length = 0;
    }

    public int get(int index){
        if (index<0 || index>length-1) return -1;
        ListNode cur = head;
        for (int i = 0;i < index;i++){
            cur = cur.next;
        }
        return cur.val;
    }

    public void addAtHead(int val){
        ListNode newHead = new ListNode(val);
        newHead.next = head;
        head = newHead;
        length++;
    }

    public void addAtTail(int val) {
        ListNode newTail = new ListNode(val);
        if (head == null) {
            head = newTail;
        }else{
            ListNode cur = head;
            while (cur.next != null ) {
                cur = cur.next;
            }
            cur.next = newTail;
        }
        length ++;
    }

    public void addAtIndex(int index, int val) {
        if (index > length ) {
            return;
        } else if (index <= 0) {
            addAtHead(val);
        } else if (index == length) {
            addAtTail(val);
        } else{
            ListNode cur = head;
            for (int i = 0; i < index-1 ; i++ ) {
                cur = cur.next;
            }
            ListNode newNode = new ListNode(val);
            newNode.next = cur.next;
            cur.next = newNode;
            length ++;
        }
    }

    public void deleteAtIndex(int index) {
        if (index > length-1 || index <0) {
            return;
        } else if (index == 0) {
            head = head.next;
        } else{
            ListNode cur = head;
            for (int i = 0; i < index-1 ; i++ ) {
                cur = cur.next;
            }
            cur.next = cur.next.next;
        }
        length --;
    }




}
