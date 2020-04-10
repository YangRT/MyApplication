package com.example.administrator.myapplication.algorithm;
import java.util.ArrayList;
import java.util.List;

/*
分割回文字符串
给定一个字符串s,将s分割成一些子串，使每个子串都是回文串
*/
public class PartitionString {

    public static List<List<String>> partition(String s){
        List<List<String>> result = new ArrayList<>();
        //dp[j][i]表示字符串s从位置j 到 i 子串为回文子串
        boolean[][] dp = new boolean[s.length()][s.length()];
        for(int i = 0;i < s.length();i++){
            for(int j = 0;j <= i;j++){
                if(s.charAt(i) == s.charAt(j) && (i - j <= 2 || dp[j+1][i-1])){
                    dp[j][i] = true;
                }
            }
        }
        helper(result,new ArrayList<String>(),dp,s,0);
        return result;
    }

    private static void helper(List<List<String>> result, List<String> path, boolean[][] dp, String s, int pos) {
        if(pos == s.length()){
            result.add(new ArrayList<>(path));
            return;
        }
        for(int i = pos;i < s.length();i++){
            if(dp[pos][i]){
                path.add(s.substring(pos,i+1));
                helper(result,path,dp,s,i+1);
                path.remove(path.size() - 1);
            }
        }
    }

    public static void main(String[] args){
        List<List<String>> re = partition("djdsskkfkkkkk");
        for(List<String> r:re){
            System.out.println(r.toString());
        }
    }

    //深度搜索
    public static List<List<String>> partionDfs(String s){
        List<List<String>> result = new ArrayList<>();
        List<String> list = new ArrayList<>();
        dfs(s,0,list,result);
        return result;
    }

    private static void dfs(String s, int pos, List<String> list, List<List<String>> result) {
        if(pos == s.length()){
            result.add(new ArrayList<String>(list));
        }else {
            for(int i = pos;i < s.length();i++){
                if(isPal(s,pos,i)){
                    list.add(s.substring(pos,i+1));
                    dfs(s,i+1,list,result);
                    list.remove(list.size() - 1);
                }
            }
        }

    }

    private static boolean isPal(String s, int low, int high) {
        while (low < high){
            if(s.charAt(low++) != s.charAt(high--)) {
                return false;
            }
        }
        return true;
    }
}