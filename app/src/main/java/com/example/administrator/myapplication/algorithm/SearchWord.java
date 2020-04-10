package com.example.administrator.myapplication.algorithm;

/*
给定一个二维网格，和一个单词，找出该单词是否存在于网格中。
单词必须根据字母顺序，由相邻格中字母构成，同一单元格不允许重复使用
 */
public class SearchWord {

    //回溯法
    private static boolean solution(char[][] bounds,String word){
        char[] wordArray = word.toCharArray();
        int[][] dirs = new int[][]{{0,1},{0,-1},{1,0},{-1,0}};
        for(int i = 0;i < bounds.length;i++){
            for(int j = 0;j < bounds[0].length;j++){
                if(dfs(bounds,dirs,i,j,wordArray,0)){
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean dfs(char[][] bounds, int[][] dirs, int i, int j, char[] wordArray, int start) {
        if(start == wordArray.length){
            return true;
        }
        if(i<0 || j<0 || i>=wordArray.length || j>=wordArray.length){
            return false;
        }
        if(bounds[i][j] == '#' || bounds[i][j] != wordArray[start]){
            return false;
        }
        boolean res = false;
        char t = bounds[i][j];
        bounds[i][j] = '#';
        for(int[] dir:dirs){
            int newRow = i + dir[0];
            int newCol = j + dir[1];
            res = dfs(bounds, dirs, newRow, newCol, wordArray, start + 1);
            if(res){
                return true;
            }
        }
        bounds[i][j] = t;
        return false;
    }


    public static void main(String[] args){


    }
}
