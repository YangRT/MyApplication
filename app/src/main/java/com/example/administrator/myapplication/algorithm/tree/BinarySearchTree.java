package com.example.administrator.myapplication.algorithm.tree;

import java.util.Stack;

// 给定一个二叉树，判断它是否是一个有效的二叉搜索树
//一个二叉搜索树必须满足下面三个条件：
//左子树的全部节点都小于根节点
//右子树的全部节点都大于根节点
//左子树和右子树都必须同样是二叉搜索树
public class BinarySearchTree {

    // 递归 需保留上下界保证所有节点符合
    public boolean isValidBST(TreeNode root) {
        return helper(root, null, null);
    }

    public boolean helper(TreeNode root, Integer max, Integer min){
        if(root == null){
            return true;
        }
        if(max != null && root.val >= max){
            return false;
        }
        if(min != null && root.val <= min){
            return false;
        }
        return helper(root.left, root.val,min) && helper(root.right, max , root.val);
    }

    // 中序遍历 判断每个元素是否小于下一个
    public boolean isValidBST2(TreeNode root) {
        Stack<TreeNode> stack = new Stack();
        double inorder = - Double.MAX_VALUE;

        while (!stack.isEmpty() || root != null) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            root = stack.pop();
            // 与上一节点比较
            if (root.val <= inorder) return false;
            inorder = root.val;
            root = root.right;
        }
        return true;
    }

}
