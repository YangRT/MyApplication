package com.example.administrator.myapplication.algorithm.tree;

// 给出一个完全二叉树，所有的树叶都在同一层，每一个父节点都有两个子节点
// 将每个节点的指针都指向和自己在同一层的、右侧的节点
// 如果节点的右侧没有节点了，那么就需要将自己的next指针设置为null
//在最初，所有节点的next指针都是null

public class NextRightPointers {

    public TreeNode solution(TreeNode root){
        helper(root);
        return root;
    }

    public void helper(TreeNode root){
        if (root == null) return;
        if (root.left != null && root.right != null){
            root.left.next = root.right;
            if(root.next != null){
                root.right.next = root.next.left;
            }
            helper(root.left);
            helper(root.right);
        }
    }

}
