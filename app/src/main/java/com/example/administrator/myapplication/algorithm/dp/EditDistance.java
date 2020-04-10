package com.example.administrator.myapplication.algorithm.dp;
/*
    给定两个单词 word1 和 word2，计算将 word1 转成 word2 所使用最少操作数
    有3种操作：①将一个字符转成另一个字符 ②将一个字符删除 ③插入一个字符
 */
public class EditDistance {
    //空间复杂度 n * m
    private static int solution(String w1,String w2){
        int w1length = w1.length();
        int w2length = w2.length();
        int[][] dp = new int[w1length+1][w2length+1];
        for(int i = 1;i <= w1length;i++){
            dp[i][0] = dp[i-1][0] + 1;
        }
        for(int j = 1;j <= w2length;j++){
            dp[0][j] = dp[0][j-1] + 1;
        }
        for(int i = 1;i <= w1length;i++){
            for(int j = 1;j <= w2length;j++){
                if(w1.charAt(i-1) == w2.charAt(j-1)){
                    dp[i][j] = dp[i-1][j-1];
                }else {
                    dp[i][j] = Math.min(dp[i-1][j-1],Math.min(dp[i][j-1],dp[i-1][j])) + 1;
                }
            }
        }
        return dp[w1length][w2length];
    }

    //优化 空间复杂度 为 n,推导 (i,j）时仅需用到dp[i-1][j],dp[i][j-1],dp[i-1][j-1].可转成一维数组
    // 用变量存储（i-1,j-1),其他两个在一维中表示为 dp[j],dp[j-1]
    private static int solution2(String s1,String s2){
        int length1 = s1.length();
        int length2 = s2.length();
        int[] dp = new int[length2+1];
        for(int i = 0;i <= length2;i++){
            dp[i] = i;
        }
        for(int i = 1;i <= length1;i++){
            int temp = dp[0];
            dp[0] = i;
            for(int j = 1;j <= length2;j++){
                int pre = temp;
                temp = dp[j];
                if(s1.charAt(i-1)==s2.charAt(j-1)){
                    dp[j] = pre;
                }else {
                    dp[j] = Math.min(Math.min(dp[j-1],pre),dp[j])+1;
                }
            }
        }
        return dp[length2];
    }
    public static void main(String[] args){
        System.out.println(solution("horse","ros"));
    }
}
