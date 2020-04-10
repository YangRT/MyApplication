package com.example.administrator.myapplication.algorithm.link_list;

import java.util.Stack;

// 给出2个非空的链表，代表两个非负整数
// 整数的最高位是链表的第一个节点
// 每个节点的value值只有一位。将这两个数相加，然后返回和的链表
// 更进一步，如果假设不能翻转链表?
public class AddTwoNum2 {

    // 翻转链表，再相加
    public ListNode solution(ListNode f1,ListNode f2){
        if (f1==null) return f2;
        if (f2==null) return f1;
        ReverseLinkedList reverseLinkedList = new ReverseLinkedList();
        f1 = reverseLinkedList.solution(f1);
        f2 = reverseLinkedList.solution(f2);

        AddTwoNum addTwoNum = new AddTwoNum();
        return  reverseLinkedList.solution(addTwoNum.solution(f1,f2));
    }

    // 栈
    public ListNode solution2(ListNode f1,ListNode f2){
        Stack<Integer> a = ListToStack(f1);
        Stack<Integer> b = ListToStack(f2);
        Stack<Integer> ans = new Stack<Integer>();

        ListNode assist = new ListNode(0);
        int carryBit = 0;
        while( (!a.empty()) ||  (!b.empty()) )
        {
            int x = (a.empty() == true ? 0 : a.pop() );
            int y = (b.empty() == true ? 0 : b.pop() );

            int sum = x + y + carryBit;
            ans.push(sum%10);
            carryBit = sum/10;
        }

        //如果还有进位，则进到最高位，需要在堆栈中再增加一位
        if(carryBit != 0){
            ans.push(carryBit);
        }
        ListNode cur = assist;
        while( !ans.empty() ){
            cur.next = new ListNode(ans.pop());
            cur = cur.next;
        }
        return assist.next;
    }

    public Stack<Integer> ListToStack(ListNode root){
        ListNode cur = root;
        Stack<Integer> s = new Stack<Integer>();
        while(cur != null){
            s.push(cur.val);
            cur = cur.next;
        }
        return s;
    }

    // 递归
    int flow=0;
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        if(l1==null) return l2;
        if(l2==null) return l1;
        ListNode res1=l1,res2=l2;
        int len1=0,len2=0;
        // 计算长度
        while(l1!=null){
            len1++;
            l1=l1.next;
        }
        while(l2!=null){
            len2++;
            l2=l2.next;
        }
        // add 第一个参数链表长度 比 后一个 长
        ListNode res=len1>len2?add(res1,res2,len1,len2):add(res2,res1,len2,len1);
        // 判断是否还有进位
        if(flow==1) {
            res1=new ListNode(1);
            res1.next=res;
            return res1;
        }
        return res;
    }
    public ListNode add(ListNode l1, ListNode l2,int len1,int len2) {
        int temp;
        // 个位
        if((len1==1)&&(len2==1)){
            temp=l1.val;
            l1.val=(l1.val+l2.val)%10;
            flow=(temp+l2.val)/10;
            return l1;
        }
        // 位置不同 先对齐
        if(len1>len2) {
            temp=l1.val;
            l1.next=add(l1.next, l2,len1-1,len2);
            l1.val=(temp+flow)%10;
            flow=(temp+flow)/10;
            return l1;
        }
        // 位置相同 先计算低位
        l1.next=add(l1.next, l2.next,len1-1,len2-1);
        // 计算当前位置
        temp=l1.val;
        l1.val=(temp+flow+l2.val)%10;
        flow=(temp+flow+l2.val)/10;
        return l1;
    }

}
