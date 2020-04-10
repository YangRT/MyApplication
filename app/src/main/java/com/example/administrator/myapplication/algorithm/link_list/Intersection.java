package com.example.administrator.myapplication.algorithm.link_list;

// 写一个程序去寻找两个单向链表相交的节点
// 时间复杂度为O（n），空间复杂度为O（1）
// 集合存储已经遍历过的链表,但空间复杂度就不是O(1)
public class Intersection {

    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if(headA == null || headB == null){
            return null;
        }
        ListNode curA = headA;
        ListNode curB = headB;
        int numA = countListLength(headA);
        int numB = countListLength(headB);
        int k = 0;
        if(numA > numB){
            k = numA - numB;
            //让A链表先走k步
            while(k != 0){
                curA = curA.next;
                k --;
            }
        }else{
            k = numB - numA;
            //让B链表先走k步
            while(k != 0){
                curB = curB.next;
                k --;
            }
        }

        return sameNode(curA,curB);
    }
    //计算链表的长度
    public int countListLength(ListNode head){
        int length = 0;
        ListNode cur = head;
        while(cur != null){
            length++;
            cur = cur.next;
        }
        return length;
    }
    //寻找两个链表交叉的节点
    public ListNode sameNode(ListNode la,ListNode lb){
        while(la != null){
            if(la == lb){
                return la;
            }else{
                la = la.next;
                lb = lb.next;
            }
        }
        return null;
    }

}
