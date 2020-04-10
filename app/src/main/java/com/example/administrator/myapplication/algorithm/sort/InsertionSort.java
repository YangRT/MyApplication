package com.example.administrator.myapplication.algorithm.sort;
//插入排序
public class InsertionSort {

    private static void insertion(int[] array){
        int size = array.length;
        int pre,current;
        for(int i = 1;i < size;i++){
            pre = i -1;
            current = array[i];
            while (pre >=0 && array[pre] > current){
                array[pre+1] = array[pre];
                pre--;
            }
            array[pre+1] = current;
        }
    }


    public static void main(String[] args){
        int[] array = new int[]{2,44,21,56,45,90,25,65,1};
        insertion(array);
        for(int i = 0;i < array.length;i++){
            System.out.print(array[i]+" ");
        }
    }
}
