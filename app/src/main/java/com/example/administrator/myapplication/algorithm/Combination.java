package com.example.administrator.myapplication.algorithm;
import java.util.ArrayList;
import java.util.List;
/*
    给定数字 n 和 k,返回 1.....n 中所有可能的 k 个数的组合
 */
public class Combination {

    private static List<List<Integer>> solution(int n, int k){
        List<List<Integer>> result = new ArrayList<>();
        int i = 0;
        List<Integer> t = new ArrayList<>();
        for(int j = 0;j < k;j++){
            t.add(0);
        }
        while(i >= 0){
            t.set(i,t.get(i)+1);
            if(t.get(i) > n){
                --i;
            }else if(i == k-1){
                result.add(new ArrayList<Integer>(t));
            }else {
                ++i;
                t.set(i,t.get(i-1));
            }
        }
        return result;
    }

    public static void main(String[] args){
        List<List<Integer>> result = solution(4,2);
        System.out.println(result.toString());
    }
}
