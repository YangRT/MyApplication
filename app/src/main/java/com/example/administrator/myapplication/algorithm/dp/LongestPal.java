package com.example.administrator.myapplication.algorithm.dp;
/*
  最长回文子串（Longest Palindromic Substring)
  给定一个字符串，返回最长回文子串
 */
public class LongestPal {

    private static String solution(String s){
        int n = s.length();
        if(n == 1) return s;
        int max = 1;
        int index = 0;
        boolean[][] dp = new boolean[s.length()][s.length()];
        for(int i = 0;i < n;i++) {
            dp[i][i] = true;
            if(i<n-1&&s.charAt(i) == s.charAt(i+1)){
                dp[i][i+1] = true;
                max = 2;
                index = i;
            }
        }
        for(int i = 3;i <= n;i++){
            for(int j = 0;j < n-i+1;j++){
                int t = j+i-1;
                if(s.charAt(j) == s.charAt(t) && dp[j+1][t-1]){
                    dp[j][t] = true;
                    max = t - j + 1;
                    index = j;
                }
            }
        }
        return s.substring(index,index+max);
    }

    public static void main(String[] args) {
        System.out.println(solution("babad"));
    }
}
