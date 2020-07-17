package com.example.administrator.myapplication.algorithm;
// 一个整型数组 nums 里除两个数字之外，其他数字都出现了两次
// 请写程序找出这两个只出现一次的数字。要求时间复杂度是O(n)，空间复杂度是O(1)。
public class TwoWord {
    // 异或：相同为0 不同为 1 出现两次的数字异或结果为0   0异或x的结果为x
    // 数组所有数字异或结果 为 两次出现一次数字异或的结果
    // 为了获取答案，将两个出现一次的数字分在不同组，而相同的数字分在同一组
    // 两个组将组内数字异或即可分别获得出现一次的数字
    // 为了分组，选择两个出现一次数字的异或结果的某个1作为分界线
    // 如果该位为 0 就分到第一组，否则就分到第二组
    // 异或结果1 代表不同 所以两个数字会被分到不同组 而相同数字各个位置都相同会被分到同一组
    public int[] singleNumbers(int[] nums) {
        // 所有数字异或结果 即两个出现一个数字异或结果
        int sum = 0;
        int[] res = new int[2];
        for(int num : nums){
            sum ^= num;
        }
        // 选取分界线 -sum 为 sum 按位取反加一 所以两者与的结果为sum的低位1
        // 如sum为 01010 -sum 即为 10110  与 结果为 00010
        int lowbit = sum & (-sum);
        for(int num : nums){
            // 分组 上例中lowbit只有一位为1 其余为0，与的结果为0
            // 所以取决于数字在lowbit为1的位置上的取值 为0则结果为0 为1即不为0
            if((num & lowbit) == 0){
                res[0] ^= num;
            }else{
                res[1] ^= num;
            }
        }
        return res;
    }

}
