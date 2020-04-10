package com.example.administrator.myapplication.algorithm;
/*
    解码：一条包含字母A-Z的消息通过以下编码
    ‘A' -》 1
     'B' -》 2
      ....
     'Z' -》 26
     给定一串数字，输出有几种解码答案
      */
public class decode {
    //动态规划  符合条件下：dp[i] = dp[i-1] + dp[i-2]
    private static int solution(String s){
        if(s == null||s.length() == 0){
            return 0;
        }
        int n = s.length();
        int[] dp = new int[n+1];
        dp[0] = 1;
        dp[1] = s.charAt(0) != '0'?1:0;
        for(int i = 2;i <=n;i++){
            int first = Integer.valueOf(s.substring(i-1,i));
            int second = Integer.valueOf(s.substring(i-2,i));
            if(first>=1 && first<=9){
                dp[i] += dp[i-1];
                System.out.println(i+":"+dp[i]);
            }
            if(second >= 10 && second <=26){
                dp[i] += dp[i-2];
                System.out.println(i+":"+dp[i]);
            }
        }
        System.out.println(dp[n]+" ");
        return dp[n];
    }

    public static void main(String[] args){
        solution("2312");
    }

    //  public List<String> wordSubsets(String[] A, String[] B) {
    //        int[] bmax = count("");
    //        for (String b: B) {
    //            int[] bCount = count(b);
    //            for (int i = 0; i < 26; ++i)
    //                bmax[i] = Math.max(bmax[i], bCount[i]);
    //        }
    //
    //        List<String> ans = new ArrayList();
    //        search: for (String a: A) {
    //            int[] aCount = count(a);
    //            for (int i = 0; i < 26; ++i)
    //                if (aCount[i] < bmax[i])
    //                    continue search;
    //            ans.add(a);
    //        }
    //
    //        return ans;
    //    }
    //
    //    public int[] count(String S) {
    //        int[] ans = new int[26];
    //        for (char c: S.toCharArray())
    //            ans[c - 'a']++;
    //        return ans;
    //    }

}
