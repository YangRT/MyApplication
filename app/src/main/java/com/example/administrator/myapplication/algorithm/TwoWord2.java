package com.example.administrator.myapplication.algorithm;
// 在一个数组 nums 中除一个数字只出现一次之外，
// 其他数字都出现了三次。请找出那个只出现一次的数字。
public class TwoWord2 {
    // 状态机 将出现3次的数字通过运算化为0
    // 对于某一位来说 以状态来代表出现次数 如果出现3次的数字在某位上为0即无影响 为1需转化成0
    // 3个状态需要2位来表示 00 --1--> 01 --1--> 10 --1--> 00
    // 如上转移表达式 将出现3次1化为0 这样数组的操作后结果即为出现一次的数字
    // 用twos 代表状态位高位 ones代表状态位低位
    // 分析可得转移方程（按位） ones = ones ^ num & ~twos;  twos = twos ^ num & ~ones;
    // num 为 数字在某位上的取值 0 或 1
    // 高位计算基于低位计算后的结果
    // 这样数组操作后各位状态仅为 00 01 取决于出现一次的数字 由于与高位无关直接返回低位即为结果
    public int singleNumber(int[] nums) {
        int ones = 0, twos = 0;
        for(int num : nums){
            ones = ones ^ num & ~twos;
            twos = twos ^ num & ~ones;
        }
        return ones;
    }

}
