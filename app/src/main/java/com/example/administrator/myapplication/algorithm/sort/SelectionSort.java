package com.example.administrator.myapplication.algorithm.sort;
//选择排序
public class SelectionSort {

    private static void selection(int[] array){
        int size = array.length;
        for(int i = 0;i < size-1;i++){
            int t = i;
            for(int j = i+1;j < size;j++){
                if(array[t] > array[j]){
                    t = j;
                }
            }
            if(t != i){
                int tmp = array[i];
                array[i] = array[t];
                array[t] = tmp;
            }
        }
    }

    public static void main(String[] args){
        int[] array = new int[]{2,44,21,56,45,90,25,65,1};
        selection(array);
        for(int i = 0;i < array.length;i++){
            System.out.print(array[i]+" ");
        }
    }

}
