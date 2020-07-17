package com.example.administrator.myapplication.algorithm;
// 顺时针打印二维数组
public class Print {

    public static void main(String[] args) {
        int[][] input = new int[][]{{1,2,3},{4,5,6},{7,8,9}};
        int[] re = spiralOrder(input);
    }

    public static int[] spiralOrder(int[][] matrix) {
        int len = matrix.length;
        if(len == 0) return new int[0];
        int row = matrix[0].length;
        int[] result = new int[len*row];
        int index = 0;
        int xStart = 0;
        int yStart = 0;
        int xEnd = row-1;
        int yEnd = len-1;
        int y = yStart;
        int x = xEnd;
        boolean diraction = true;
        while(true){
            if(diraction){
                for(int i = xStart;i <= xEnd;i++){
                    result[index]= matrix[y][i];
                    index++;
                }
                for(int j = yStart+1;j <= yEnd;j++){
                    result[index] = matrix[j][x];
                    index++;
                }
                if(xStart<xEnd) xEnd = xEnd-1;
                if(yStart<yEnd) yStart = yStart+1;
                y = yEnd;
                x = xStart;
            }else{
                for(int i = xEnd;i >= xStart;i--){
                    result[index]= matrix[y][i];
                    index++;
                }
                for(int j = yEnd-1;j >= yStart;j--){
                    result[index] = matrix[j][x];
                    index++;
                }
                if(xStart<xEnd) xStart = xStart+1;
                if(yStart<yEnd) yEnd = yEnd-1;
                x = xEnd;
                y = yStart;
            }
            if(index == len*row) break;
            diraction = diraction ?false:true;
        }
        return result;

    }
}
