package com.example.administrator.myapplication.algorithm.tree;

import java.util.ArrayList;
import java.util.List;

// 给出一棵树的前序遍历和中序遍历
// 根据这两种遍历顺序构造一棵二叉树
// 你可以假设，树中没有重复的节点
public class ConstructBinaryTree {

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        return helper(preorder, 0, preorder.length-1, inorder, 0, inorder.length-1);
    }

    public TreeNode helper(int[] preorder, int p_start, int p_end, int[] inorder , int i_start, int i_end){
        if(p_start > p_end || i_start > i_end){
            return null;
        }
        int rootVal = preorder[p_start];//根节点是先序遍历的第一个节点，rootVal是根节点的值
        int rootIndex = 0;//rootIndex是根节点在中序遍历数组中的下标

        //在中序遍历数组中找到根节点的下标
        for(int i = i_start ; i <= i_end ; i++ ){
            if(inorder[i] == rootVal){
                rootIndex = i;
                break;
            }
        }

        //在中序遍历的数组中，[i_start, rootIndex-1]之间部分是左子树，
        //[rootIndex+1, i_end]之间的是右子树

        //在先序遍历的数组中，[p_start+1 , p_start+length]之间部分是左子树，
        //[p_start+length+1 , p_end]之间的是右子树

        //length是左子树的节点个数
        int length = rootIndex - i_start;

        TreeNode temp = new TreeNode(rootVal);
        temp.left  = helper(preorder, p_start+1, p_start+length, inorder, i_start, rootIndex-1);
        temp.right = helper(preorder, p_start+length+1, p_end, inorder, rootIndex+1, i_end);

        return temp;
    }

}
