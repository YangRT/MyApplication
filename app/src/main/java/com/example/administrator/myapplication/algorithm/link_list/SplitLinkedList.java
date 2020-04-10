package com.example.administrator.myapplication.algorithm.link_list;

// 给定一个单向链表及其根节点 root ，写一个函数分割这个链表
// 这个函数要把链表分割为 k 个连续的部分
// 每个部分的长度需要尽可能相等。任意两个部分的长度最多相差1.
// 有可能一些部分的长度是0, 这些部分的顺序需要按照原来链表的顺序排列
// 前面的部分的长度，需要大于或者等于后面部分的长度
// 返回一个链表的数组，代表这个链表被分割的部分
public class SplitLinkedList {

    public ListNode[] splitListToParts(ListNode root, int k) {

        if(root == null){
            return new ListNode[k];
        }

        //计算链表的总长度
        int length = countLength(root);

        ListNode [] ans = new ListNode[k];
        int Remainder = length % k;
        int Quotient = length / k;

        ListNode cur = root;
        for(int i = 0; i<k ; i++){
            //第i个链表的长度是sublength，它
            //可能是length/k
            //也可能是length/k + 1
            //也可能是0
            int sublength = 0;
            if(Remainder != 0){
                sublength = Quotient + 1;
                Remainder--;
            }else{
                sublength = Quotient;
            }
            if(cur == null ){
                //此种情况为k大于链表的长度时，cur已经走到了链表结尾
                //此时第i个链表长度为0
                ans[i] = null;
                continue;
            }

            //从总链表中截取sublength长度的链表作为第i个链表
            ListNode temp = cur;
            ListNode curPre = null;
            for(int j = 0 ; j < sublength ; j++){
                curPre = cur;
                cur = cur.next;
            }
            curPre.next = null;
            ans[i] = temp;
        }

        return ans;
    }
    public int countLength(ListNode root){
        int length = 0;
        ListNode cur = root;
        while(cur != null){
            length++;
            cur = cur.next;
        }
        return length;
    }
}
