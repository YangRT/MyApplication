package com.example.administrator.myapplication.algorithm.dp;
/*
 正则表达式匹配（Regular Expression Matching)
     给你一个字符串 S 和一个正则表达式 P（仅包含 ‘.' '*' 预定字符 和 普通字符）
     判断P是否能完全匹配S
     ex：
     input: S="aa" P="a" output: false
     input: S="aa" P="a*" output:true
     input: S="aab" P="c*a*b" output：true
 */
public class REMatching {

    private static boolean solution(String s,String p){
        if(p.length() == 0) return false;
        boolean[][] dp = new boolean[s.length()+1][p.length()+1];
        dp[0][0] = true;
        if (s.charAt(0) == p.charAt(0) || p.charAt(0) == '.'){
            dp[1][1] = true;
        }
        //当字符串长度为0时
        for(int j = 2;j <= p.length();j+=2){
            dp[0][j] = dp[0][j-2] && p.charAt(j-1) == '*';
        }
        for(int i = 1;i <= s.length();i++){
            for(int j = 2;j <= p.length();j++){
                if (s.charAt(i-1) == p.charAt(j-1) || p.charAt(j-1) == '.'){
                     dp[i][j] = dp[i-1][j-1];
                }else {
                    if(p.charAt(j-1) == '*'){
                        if(s.charAt(i-1) == p.charAt(j-2) || p.charAt(j-2) == '.'){
                            dp[i][j] = dp[i-1][j];
                        }else {
                            dp[i][j] = dp[i][j-1];
                        }
                    }
                }
            }
        }
        return dp[s.length()-1][p.length()-1];
    }

    public static void main(String[] args){
        System.out.println(solution("mississippi","mis*is*p*"));
    }
}
