package com.example.administrator.myapplication.algorithm.link_list;

//给出一个单向链表，需要将所有奇数节点放在偶数节点的前面
// 这里的奇数和偶数，是节点的顺序编号，而不是节点的val值
//要求：空间复杂度：O(1)，时间复杂度O(n)，n是链表的总结点数
public class OddEven {

    // 使用一个奇数指针 odd 指向 head ，一个偶数指针 even 指向 head.next
    //接着，定义一个 cur 指针。cur 一开始指向 head.next.next
    // 使用 cur 遍历数组，cur 每次都要走两步
    // 这样可以使得 cur 所指的节点都是奇数节点，cur.next 指向的节点都是偶数节点
    //使用奇数指针 odd 和偶数指针 even 分别为奇数链表和偶数链表连接节点
    // 每次奇数链表都会连上cur 节点，偶数链表都会连上 cur.next 节点
    //最后，将两个链表拼在一起就行了

    public ListNode oddEvenList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode odd = head;
        ListNode even = head.next;
        ListNode evenHead = head.next;
        ListNode cur = head.next.next;
        while(cur != null && cur.next != null){
            odd.next = cur;
            odd = odd.next;
            even.next = cur.next;
            even = even.next;
            cur = cur.next.next;
        }
        even.next = null;
        if(cur != null){
            odd.next = cur;
            odd = odd.next;
        }
        odd.next = evenHead;

        return head;
    }

}
