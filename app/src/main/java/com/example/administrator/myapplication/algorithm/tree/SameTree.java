package com.example.administrator.myapplication.algorithm.tree;

// 给出两个二叉树，判断它们是否相同
// 相同的定义是，每个对应位置的节点的值都相同，树的形状结构也相同
public class SameTree {

    public boolean isSameTree(TreeNode p, TreeNode q) {
        if(p == null && q == null){
            return true;
        }
        if(p == null || q == null){
            return false;
        }
        if(!p.val.equals(q.val)){
            return false;
        }
        return isSameTree(p.left, q.left) && isSameTree(p.right,q.right);
    }

}
