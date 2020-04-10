package com.example.administrator.myapplication.algorithm.sort;
//快速排序
public class QuickSort {

    private static int partition(int[] array,int start,int end){
        int x = array[start];
        int p = start;
        start++;
        while (true){
            while(start < end && array[start] < x) start++;
            while (array[end]>x) end--;
            if(start >= end) break;
            int t = array[start];
            array[start] =array[end];
            array[end] = t;
        }
        int t = array[end];
        array[end] =array[p];
        array[p] = t;
        return end;
    }



    private static void quick(int[] array,int start,int end){
        if(start >= end) return;
        int p = partition(array,start,end);
        quick(array,start,p-1);
        quick(array,p+1,end);
    }

    public static void main(String[] args){
        int[] array = new int[]{2,44,21,56,45,90,25,65,1};
        quick(array,0,8);
        for(int i = 0;i < array.length;i++){
            System.out.print(array[i]+" ");
        }
    }


}
