package com.example.administrator.myapplication.algorithm;

/*
* 给定一个二维矩阵，包含 X 和 O，找到所有被 X 围绕的区域，并将这些区域里所有的 O
* 替换为 X
* */

public class AroundArea {
    //从边界找O，然后遍历搜索相邻的O标记为*，这些O都不被包围
    private static void solution(char[][] area){
        if(area == null || area.length == 0){
            return;
        }
        for(int i = 0;i < area.length;i++){
            for(int j = 0;j < area[0].length;j++){
                if(i == 0 ||i == area.length-1 || j == 0 || j == area[0].length-1){
                    if(area[i][j] == 'O'){
                        dfs(i,j,area);
                    }
                }
            }
        }
        for(int i = 0;i < area.length;i++){
            for(int j = 0;j < area[0].length;j++){
                if(area[i][j] == '*'){
                    area[i][j] = 'O';
                }else {
                    area[i][j] = 'X';
                }
            }
        }
        return;
    }

    private static void dfs(int i, int j, char[][] area) {
        if(i<0 || i>=area.length || j<0 || j >=area[0].length){
            return;
        }
        if(area[i][j] == 'X' || area[i][j] == '*'){
            return;
        }
        area[i][j] = '*';
        dfs(i+1,j,area);
        dfs(i-1,j,area);
        dfs(i,j+1,area);
        dfs(i,j-1,area);
    }


    public static void main(String[] args){
        char[][] area = {{'X','X','X','X'},{'X','X','O','X'},{'X','O','X','X'},{'X','O','X','X'}};
        solution(area);
        for(int i = 0;i < area.length;i++){
            for(int j = 0;j < area[0].length;j++){
                System.out.print(area[i][j]);
            }
            System.out.println("");
        }
    }
}
