package com.example.administrator.myapplication.algorithm.link_list;

// 给出两个非空的链表，代表着2个非负整数
// 链表的第一个节点是整数的个位，第二个是十位，依次类推，顺序和整数的顺序相反
// 比如(2 -> 4 -> 3)代表的是整数342
//求这2个非负整数的和，并且用同样的方式，使用链表表示出来。
public class AddTwoNum {

    public ListNode solution(ListNode f,ListNode e){
        int carryBit = 0; // 进位
        int num = 0;   // 剩余数
        ListNode ans = new ListNode(0);
        ListNode temp = ans;
        while (f != null || e != null){
            int fVal = f==null?0:f.val;
            int eVal = e==null?0:e.val;
            num = (fVal+eVal+carryBit)%10;
            carryBit = (fVal+eVal+carryBit)/10;
            temp.next = new ListNode(num);
            temp = temp.next;
            if (f!=null) f = f.next;
            if (e!=null) e = e.next;
        }
        if(carryBit != 0) {
            temp.next = new ListNode(carryBit);
        }
        return ans.next;
    }
}
