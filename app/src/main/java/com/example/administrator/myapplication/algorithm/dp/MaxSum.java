package com.example.administrator.myapplication.algorithm.dp;
/*
    给定一个整数数组，求最大子序列和
 */
public class MaxSum {

    private static int solution(int[] array){
        int[] dp = new int[array.length];
        dp[0] = array[0];
        for(int i = 1;i < array.length;i++){
            if(dp[i-1] < 0){
                dp[i] = array[i];
            }else {
                dp[i] = dp[i-1] + array[i];
            }
        }
        int max = -999999;
        for(int j = 0;j < array.length;j++){
            if(dp[j] > max) max = dp[j];
        }
        return max;
    }

    public static void main(String[] args){
        int[] array = new int[]{-2,1,-3,4,-1,2,1,-5,4};
        System.out.println(solution(array));
    }
}
