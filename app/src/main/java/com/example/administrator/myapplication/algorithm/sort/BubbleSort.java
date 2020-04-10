package com.example.administrator.myapplication.algorithm.sort;
//冒泡排序
public class BubbleSort {

    private static void Bubble(int[] array){
        int length = array.length;
        for(int i = length-1;i>= 1;i--){
            for(int j = 0;j < i;j++){
                if(array[j] > array[j+1]){
                    int t = array[j];
                    array[j] = array[j+1];
                    array[j+1] = t;
                }
            }
        }
    }

    public static void main(String[] args){
        int[] array = new int[]{2,44,21,56,45,90,25,65,1};
        Bubble(array);
        for(int i = 0;i < array.length;i++){
            System.out.print(array[i]+" ");
        }
    }



}
