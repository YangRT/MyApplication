package com.example.administrator.myapplication.algorithm.dp;
/*
    一只青蛙一次可以跳1级台阶，也可以跳2级台阶，求青蛙跳上
    n级台阶总共有多少种方法。
 */
public class Jump {

    private static int solution(int n){
        if(n <= 1) return n;
        int[] dp = new int[n];
        dp[0] = 1;
        dp[1] = 2;
        for(int i = 2;i < n;i++){
            dp[i] = dp[i-1] + dp[i-2];
        }
        return dp[n-1];
    }

    public static void main(String[] args){
        System.out.println(solution(9));
    }
}
