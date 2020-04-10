package com.example.administrator.myapplication.algorithm.dp;

import java.util.Scanner;

/*
写一个程序来查找从最高点到底部任意处结束的路径，使路径经过数字的和最大。
        7
      3   8
    8   1   0
  2   7   4   4
4   5   2   6   5

 */
public class NumberTriangle {

    private static void solution(int[][] array){
        int n = array.length;
        int[][] dp = new int[n][n];
        for (int i = 0;i < n;i++){
            dp[n-1][i] = array[n-1][i];
        }
        for (int i = n-2;i >= 0;i--){
            for (int j =0;j <= i;j++){
                dp[i][j] = Math.max(dp[i+1][j],dp[i+1][j+1]) + array[i][j];
            }
        }
        System.out.println(dp[0][0]);
    }

    public static void main(String[] args) {
        int n;
        Scanner s=new Scanner(System.in);
        n = s.nextInt();
        int[][] array = new int[n][n];
        for (int i = 0;i < n;i++){
            Scanner scanner=new Scanner(System.in);
            for (int j = 0;j < i+1;j++){
                array[i][j] = scanner.nextInt();
            }
        }
        solution(array);
    }
}
