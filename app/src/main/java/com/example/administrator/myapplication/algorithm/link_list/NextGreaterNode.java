package com.example.administrator.myapplication.algorithm.link_list;

import com.airbnb.lottie.L;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

// 给出一个链表，按照节点出现的顺序，我们给链表节点编号：
//node_1, node_2, node_3, …
// 每一个节点都有 “下一个最大值” 。对于 node_i 来说，它的下一个最大值是node_j.val
//这个 j 需要满足下面三个条件：
//1.j > i
//2.node_j.val > node_i.val
//3.如果满足上面两个条件的 j 不止一个，那么就从中取最小的 j
//如果不存在同时满足上面三个条件的 j ，则 node_i 的 “下一个最大值” 是0
//返回一个整数数组 answer，answer[ i ] = next_larger(node_{i+1})
// 也就是第 i 个数组元素的值，是第 i +1 个节点的“下一个最大值”
public class NextGreaterNode {

    // 暴力查找
    public static int[] solution(ListNode head){
        if (head == null) return new int[0];
        int size = 0;
        ListNode temp = new ListNode();
        temp.next = head;
        while (temp.next != null){
            size++;
            temp = temp.next;
        }
        int[] dp = new int[size];
        temp = null;
        ListNode first = head;
        for(int i = 0;i < size-1;i++){
            temp = first.next;
            while (temp != null){
                if (first.val < temp.val){
                    dp[i] = temp.val;
                    break;
                }
                temp = temp.next;
            }
            first = first.next;
        }
        return dp;
    }

    // 将链表转成list 倒序查找，栈保存所有可能结果
    public int[] nextLargerNodes(ListNode head) {
        if(head == null ){
            return new int[0];
        }
        //由于需要倒序计算，因此先将链表转换为ArrayList
        List<Integer> list = new ArrayList<Integer>();
        ListNode cur = head;
        while(cur != null){
            list.add(cur.val);
            cur = cur.next;
        }
        int length = list.size();
        int [] ans = new int[length];//ans就是要返回的数组
        //堆栈中存放所有满足条件的节点val值
        Stack<Integer> s = new Stack<Integer>();

        for(int i = length-1 ; i >= 0; i-- ){
            //如果堆栈不空，就要弹出所有小于当前节点的值
            //以保证堆栈中存储的都是“所有满足条件的节点val值”
            while( s.isEmpty() == false ){
                int temp = s.peek();//peek函数取栈顶元素，但是不弹出
                if(temp > list.get(i)){
                    ans[i] = temp;
                    s.push(list.get(i));
                    break;
                }
                else{//将不符合条件的剔除
                    //如果堆栈中没有满足条件的值，这个堆栈会被删空
                    s.pop();
                }
            }
            //堆栈为空，可能是在上面的while循环中被删空了，可能是第一遍循环还没有放入值
            //这时就应该将第 i 个节点中的val值放入堆栈，ans[i] 直接放入0
            if(s.isEmpty() == true){
                ans[i] = 0;
                s.push(list.get(i));
            }

        }
        return ans;
    }

}
