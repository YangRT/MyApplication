package com.example.administrator.myapplication.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
在一条环路上有N个加油站，其中第i个加油站有汽油 gas[i] 升。
你有一辆油箱容量无限的的汽车，从第i个加油站开往第i+1个加油站需要消耗汽油cost[i]升。
你从其中的一个加油站出发，开始时油箱为空。
如果你可以绕环路行驶一周，则返回出发时加油站的编号，否则返回 -1。
 */
public class GasStation {
    /**
     *
     * @param gas  //每个油站汽油
     * @param cos  //从第i站到下一站消耗汽油
     * @param begin //开始加油站编号（由0开始编号）
     */
    private static void solution(int[] gas,int[] cos,int begin){
       //汽车油数
        int myGas = 0;
        //到达站数
        int n = 0;
        //所有站都不满足
        if(begin == gas.length / 2){
            System.out.println("-1");
            return;
        }
        for(int i = begin;i <= gas.length-1;i++){
            //到达站数为总站数，即能够环绕一周
            if(n == gas.length / 2){
                System.out.println(begin+" ");
                break;
            }
            myGas += gas[i];
            //能够到达下一站
            if(myGas >= cos[i]){
                myGas -= cos[i];
                n++;
            }else {
                //以下一站为起点
                solution(gas,cos,begin+1);
                break;
            }
        }
    }


    public static void main(String[] args){
        //处理输入数据
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        String s1 = scanner.nextLine();
        s = s.replace(" ","");
        s1 = s1.replace(" ","");
        int[] gas = new int[s.length()*2];
        int[] cos = new int[s1.length()*2];
        //将环状化为线状
        for (int i = 0;i < s.length();i++){
            gas[i] = (int)s.charAt(i);
            gas[i+s.length()] = gas[i];
        }
        for(int j = 0;j < s1.length();j++){
            cos[j] = (int)s1.charAt(j);
            cos[j+s1.length()] = cos[j];
        }
        solution(gas,cos,0);
    }



}
