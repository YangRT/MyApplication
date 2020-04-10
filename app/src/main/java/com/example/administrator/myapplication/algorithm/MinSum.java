package com.example.administrator.myapplication.algorithm;

import com.airbnb.lottie.L;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 给定一个三角形，找出自顶向下的最小路径和。
 【
      [2]
    [3,4]
   [6,5,7]
  [4,1,8,3]
 】

 */
public class MinSum {
    //从下往上找
    private static int solution(List<List<Integer>> triangle){
        if(triangle.size() == 0){
            return 0;
        }
        for(int i = triangle.size()-2;i>=0;i--){
            for(int j = 0;j <=i;j++){
                List<Integer> next = triangle.get(i+1);
                int sum = Math.min(next.get(j),next.get(j+1))+triangle.get(i).get(j);
                triangle.get(i).set(j,sum);
            }
        }
        return triangle.get(0).get(0);
    }

    public static void main(String[] args){

    }


    //递归，保存计算结果
    public int minimumTotal(List<List<Integer>> triangle){
        return minimumTotal(triangle,0,0,triangle.size(),new HashMap<String, Integer>());
    }

    public int minimumTotal(List<List<Integer>> triangle, int line, int row, int total, Map<String,Integer> map){
        if(line >= total || row >= total){
            return 0;
        }
        String key = line + "-" + row;
        Integer sum;
        if((sum=map.get(key))!= null){
            return sum;
        }
        int left = minimumTotal(triangle,line+1,row,total,map);
        int right = minimumTotal(triangle,line+1,row+1,total,map);
        sum = triangle.get(line).get(row) + (left < right ? left:right);
        map.put(key,sum);
        return sum;
    }
}
