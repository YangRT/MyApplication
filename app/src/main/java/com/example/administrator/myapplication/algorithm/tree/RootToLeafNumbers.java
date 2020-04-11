package com.example.administrator.myapplication.algorithm.tree;

// 给出一个二叉树，每个节点有一个0-9的个位数
// 每一个从根节点到叶子节点的路径都可以代表一个数字
//例如，路径1->2->3代表了数字123
//寻找所有从根节点开始，在叶子节点结束的路径所代表的数字之和。
//叶子节点就是指没有子节点的节点

public class RootToLeafNumbers {

    public int sumNumbers(TreeNode root) {
        if(root == null){
            return 0;
        }
        return sum(root , 0);
    }
    public int sum(TreeNode node, int total){
        if(node == null){
            return 0;
        }
        total += node.val;
        if(node.left == null && node.right == null){
            return total;
        }
        return sum(node.left, total * 10) + sum(node.right , total*10);
    }
}
