package com.example.administrator.myapplication.algorithm;
/*
第 i 本书的厚度为 books[i][0]，高度为 books[i][1]。
按顺序 将这些书摆放到总宽度为 shelf_width 的书架上，
每一层所摆放的书的最大高度就是这一层书架的层高，书架整体的高度为各层高之和。
以这种方式布置书架，返回书架整体可能的最小高度。
 */
public class FillBookshelves {
    //动态规划  dp[i] 取到第 i 本书最低高度
    //  转移方程：dp[i] = min(dp[i],dp[j-1]+max)
    //  max为i所在层最高高度，j - i 处于同一层
    //  子问题最优解：当前的书books[i]要尽量和前面的书放到一排
    //  向前遍历，表示在第j个位置开始了新一层，j - i 处于同一层，条件为书架宽度限制
    private static int solution(int[][] books,int self_width){
        int[] dp = new int[books.length];
        dp[0] = books[0][1];
        for(int i =1;i < books.length;i++){
            int max = books[i][1];
            dp[i] = dp[i-1] +max;
            int width = books[i][0];
            for(int j= i-1;j >= 0 && width+books[j][0]<=self_width;j--){
                max = Math.max(max,books[j][1]);
                if(j-1 < 0) dp[i] = max;
                else dp[i] = Math.min(dp[i],dp[j-1]+max);
                width += books[j][0];
            }
            System.out.println(i+":"+dp[i]);
        }
        return dp[books.length-1];
    }

    public static void main(String[] args){
        int[][] books ={{1,1},{2,3},{2,3},{1,1},{1,1},{1,1},{1,2}};
        int self_width = 4;
        int result = solution(books,self_width);
        System.out.println(result+" ");
    }
}
