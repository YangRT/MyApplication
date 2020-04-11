package com.example.administrator.myapplication.algorithm.tree;

import java.util.ArrayList;
import java.util.List;

// 给定一个二叉树，返回整棵树的倾斜度
//一个节点的倾斜度的定义：所有左子树节点值的总和与所有右子树节点值的总和之间的绝对差
// 空节点，倾斜度为 0
//整棵树的倾斜度定义：所有节点倾斜度的总和
public class BinaryTreeTilt {

    List<Integer> result = new ArrayList<>();

    // 递归 获取左右子树节点值总和
    // 计算当前节点的倾斜度放入列表
    // 并返回以当前节点为root的树节点值总和
    public int solution(TreeNode root){
        count(root);
        int ans = 0;
        for(int i = 0;i < result.size();i++){
            ans += result.get(i);
        }
        return ans;
    }

    public int count(TreeNode root){
        if (root == null) return 0;
        int left = count(root.left);
        int right = count(root.right);
        int tilt = Math.abs(left-right);
        result.add(tilt);
        return left+right+root.val;
    }
}
