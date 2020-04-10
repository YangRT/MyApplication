package com.example.administrator.myapplication.algorithm.dp;
/*
    一个机器人位于一个 m×n 的网格左上角，机器人每次只能向下或
    向右移动一步，问机器人到达网格右下角有多少条不同路径。
 */
public class LoadNum {
    // 空间复杂度 m*n
    private static int solution(int m,int n){
        if(m <= 0 || n <= 0) return 0;
        int[][] dp = new int[m][n];
        for(int i = 0;i < n;i++){
            dp[0][i] = 1;
        }
        for(int j = 0;j < m;j++){
            dp[j][0] = 1;
        }
        for(int i = 1;i < m;i++){
            for(int j = 1;j < n;j++){
                dp[i][j] = dp[i-1][j] + dp[i][j-1];
            }
        }
        return dp[m-1][n-1];
    }

    //优化 空间复杂度 n  使用一维数组，因为推导 i 行时只需要 i - 1 行的数据
    //转移方程为  dp[i] = dp[i-1] + dp[i];
    private static int solution2(int m,int n){
        if(m <= 0 || n <= 0) return 0;
        int[] dp = new int[n];
        for(int i = 0;i < n;i++){
            dp[i] = 1;
        }
        for(int i = 1;i< m;i++){
            dp[0] = 1;
            for(int j = 1;j < n;j++){
                dp[j] = dp[j-1] + dp[j];
            }
        }
        return dp[n-1];
    }

    public static void main(String[] args){
        System.out.println(solution(7,3));
    }
}
