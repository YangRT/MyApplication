package com.example.administrator.myapplication.algorithm;

import java.util.LinkedList;
import java.util.List;

/*
    给出集合[1,2,3...n] 按大小顺序给出 n!排列
 */
public class Permutation {

    private static String getPermutation(int n,int k){
        List<Integer> num  = new LinkedList<>();
        for(int i = 1;i <= n;i++){
            num.add(i);
        }
        //阶乘值
        int[] fact = new int[n];
        fact[0] = 1;
        for(int i = 1;i < n;i++){
            fact[i] =i * fact[i-1];
        }
        k = k - 1;
        StringBuilder builder = new StringBuilder();
        for(int i = n;i > 0;i--){
            //从第一位开始找
            int end = k / fact[i-1];
            k = k % fact[i-1];
            builder.append(num.get(end));
            num.remove(end);
        }
        return builder.toString();
    }


    public static void main(String[] args){
        String result = getPermutation(4,9);
        System.out.println(result);
    }
}
