package com.example.administrator.myapplication.algorithm;

import java.util.List;
/*
    给定一个非空字符串s和一个包含非空单词列表字典，判定s 是否可以
    被拆分为一个或多个字典中单词（字典中单词可以重复使用）
 */
public class PartitionWord {
    //动态规划
    private static boolean solution(String word, List<String> dict){
        int length = word.length();
        //f[n]代表前n个字符可以划分
        boolean[] f = new boolean[length+1];
        f[0] = true;
        for(int i = 1;i < length+1;i++){
            for(int j = 0;j < i;j++){
                if(f[j] && dict.contains(word.substring(j,i))){
                    f[i] = true;
                    break;
                }
            }
        }
        return f[length];
    }

    //递归
    private static boolean solution2(String word,List<String> dict){
        return wordBreak(word,dict,0);
    }

    private static boolean wordBreak(String s,List<String> dict,int start){
        if(start == s.length()){
            return true;
        }
        for(int end = start+1;end <= s.length();end++){
            if(dict.contains(s.substring(start,end))&& wordBreak(s,dict,end)){
                return true;
            }
        }
        return false;
    }


    public static void main(String[] args){
        String s = "ABCDEFG";
        String v = s.substring(1,3);
        System.out.println(v);
    }
}
