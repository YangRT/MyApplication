package com.example.administrator.myapplication.algorithm.tree;

// 给出一个二叉树，将其转化为一棵只有右节点的树
// 并且需要按照先序遍历的顺序排列
public class TreeToLinkedList {

    // 找到当前节点在先序情况下右子树的前继节点(在左子树)，将右子树移至成该节点的右子树
    // 再将当前节点的左子树变成右子树，并将当前节点左子树置空
    public void flatten(TreeNode root) {
        while(root != null){
            if(root.left != null){
                //寻找左子节点，找到后，一直向右
                //找到最右侧的节点
                TreeNode pre = root.left;
                while(pre.right != null){
                    pre = pre.right;
                }

                //将根结点原本的右子节点
                //接到之前找到的最右侧节点上
                pre.right = root.right;

                //将左子节点替换为右子节点
                root.right = root.left;

                //将原来根结点的左子节点置空
                root.left = null;
            }
            root = root.right;
        }
    }


}
