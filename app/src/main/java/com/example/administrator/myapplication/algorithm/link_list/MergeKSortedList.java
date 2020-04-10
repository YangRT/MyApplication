package com.example.administrator.myapplication.algorithm.link_list;

// 合并 k 个有序链表到一个链表中，合并后的新链表也必须是有序的
public class MergeKSortedList {

    // 不断地合并2个
    public ListNode solution(ListNode[] list){
        if (list.length == 0) return null;
        MergeSortedList mergeSortedList = new MergeSortedList();
        ListNode head = new ListNode();
        for(int i = 0;i < list.length-1;i++){
            head = mergeSortedList.solution(list[i],list[i+1]);
        }
        return head;
    }

    // 分治法合并2个链表
    public ListNode solution2(ListNode[] list){
        if (list.length == 0) return null;
        MergeSortedList mergeSortedList = new MergeSortedList();
        ListNode head = divideAndConquer(list,0,list.length-1);
        return head;
    }

    // 不断划分
    public ListNode divideAndConquer(ListNode[] lists, int a,int b){
        if (a == b) {
            return lists[a];
        }
        int middle = (a + b) / 2 ;
        ListNode left  = divideAndConquer(lists, a  , middle);
        ListNode right = divideAndConquer(lists, middle+1 , b );

        return MergeSortedList.solution2(left,right);
    }

}
