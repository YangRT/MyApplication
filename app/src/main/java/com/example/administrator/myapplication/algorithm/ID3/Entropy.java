package com.example.administrator.myapplication.algorithm.ID3;

import java.util.Map;

public class Entropy {

    //信息熵
    public static double getEntropy(int x,int total){
        if(x == 0) return 0;
        double x_pi = get(x,total);
        return -(x_pi*getResult(x_pi));
    }

    public static double getResult(double y){
        return Math.log(y) / Math.log(2);
    }

    public static double get(int x,int total){
        return x * Double.parseDouble("1.0")/total;
    }

}
