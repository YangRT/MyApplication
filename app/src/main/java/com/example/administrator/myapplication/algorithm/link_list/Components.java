package com.example.administrator.myapplication.algorithm.link_list;

import java.util.HashSet;
import java.util.Set;

// 给出一个链表。这个链表中，每个节点的 val 值都是独一无二的整数
//同样也给出一个数组G。数组G中的元素是链表所有节点 val 值的子集
//如果某个节点的 val 值在数组G中，那么这个节点就被看作一个“组件”
// “组件”还有一个特性，那就是相邻的“组件”，仍然被视为一个“组件”
// 最终求整个链表一共有多少个“组件”？
public class Components {

    public int solution(ListNode head,int[] G){
        Set<Integer> Gset = new HashSet();
        for (int x: G) Gset.add(x);
        ListNode cur = head;
        int ans = 0;
        while (cur != null) {
            if (Gset.contains(cur.val) &&
                    (cur.next == null || !Gset.contains(cur.next.val)))
                ans++;
            cur = cur.next;
        }

        return ans;
    }

}
