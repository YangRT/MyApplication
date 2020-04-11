package com.example.administrator.myapplication.algorithm.tree;

// 给出一个没有重复数字的整数数组。在这个数组的基础上，建立一个最大的树
//最大的树的定义是：
//根节点是数组中最大的数字
//数组中最大的数字将数组一分为二，根节点的左子树是左半部分数组的最大的树
// 根节点的右子树是右半部分数组最大的树
//根据给出的数组构建一棵最大的树，并且返回这棵树的根节点

public class MaximumBinaryTree {

    public TreeNode constructMaximumBinaryTree(int[] nums) {
        return helper(0,nums.length-1,nums);
    }

    // 递归
    public TreeNode helper(int start, int end, int[] nums){
        if(start > end ){
            return null;
        }
        int max = 0;
        int max_index = start;
        for(int i = start ; i <=end ; i++){
            if(nums[i] > max){
                max = nums[i];
                max_index = i;
            }
        }

        TreeNode root = new TreeNode(nums[max_index]);
        root.left  = helper(start, max_index-1,nums);
        root.right = helper(max_index+1 , end, nums);
        return root;
    }

}
