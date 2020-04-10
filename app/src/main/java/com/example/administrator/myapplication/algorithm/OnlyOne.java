package com.example.administrator.myapplication.algorithm;

import java.util.HashMap;
import java.util.Map;

/*
 给定一个非空整数数组，除了某个元素只出现一次外，其余均出现三次，找出出现一次的元素
 */
public class OnlyOne {

    //一般写法
    private static int solution(int[] array){
        int length = array.length;
        Map<Integer,Integer> map = new HashMap<>();
        for(int i = 0;i < length;i++){
            if(map.containsKey(array[i])){
                int t = map.get(array[i]);
                map.put(array[i],++t);
            }else {
                map.put(array[i],1);
            }
        }
        int result=0;
        for(Integer key:map.keySet()){
            if(map.get(key) == 1){
                result = key;
                break;
            }
        }
        return result;
    }

    //https://blog.csdn.net/jiangxiewei/article/details/82227451
    //建造一个计数器，ab表示状态，a为高位，b为低位
    private static int singleNumber(int[] nums){
        int a=0,b=0;
        for(int j = 0;j < nums.length;j++){
            b = (b ^ nums[j])& ~a;
            a = (a ^ nums[j])& ~b;
        }
        return b;
    }

    private static int singleNumber2(int[] nums){
        int a = 0,b = 0,mask = 0;
        for(int i:nums){
            b = a & i;
            a ^= i;
            mask = ~(a & b);
            b &= mask;
            a &= mask;
        }
        return a;
    }

    private static int singleNumber3(int[] nums){
        int a =0,b = 0;
        for(int j :nums){
            int t = (~a & b & j) | (a & ~b & ~j);
            a = t;
        }
        return a | b;
    }

    //位存储
    private static int singleNumber4(int[] nums){
        int ans = 0;
        for(int i = 0;i < 32;i++){
            int sum = 0;
            for(int j=0;j < nums.length;j++){
                if(((nums[j]>>1)&1) == 1){
                    sum++;
                    sum %= 3;
                }
            }
            if(sum != 0){
                ans |= sum<< i;
            }
        }
        return ans;
    }

    public static void main(String[] args){
        int[] in = new int[]{6,5,6,99,5,5,6};
        System.out.println(singleNumber(in));
    }
}
