package com.example.administrator.myapplication.algorithm.tree;

// 给出一个二叉搜索树，以及一个值，要把这个值插入二叉搜索树
// 返回插入值之后的新树。题目保证要插入的值，在原来的二叉搜索树中不存在
//需要注意的是，也许存在多种有效的插入方式，只要新树仍然是一棵二叉搜索树就行
// 如果有多种插入方式，你可以返回任意一种
//一棵树是二叉搜索树，意味着该树上每一个节点上的值
// 都要大于所有的左子树上的节点
// 小于所有右子树上的节点

public class InsertNode {

    public TreeNode insertIntoBST(TreeNode root, int val) {
        if(root == null){
            return new TreeNode(val);
        }
        if(root.val > val){
            root.left  = insertIntoBST(root.left , val);
        }else{
            root.right = insertIntoBST(root.right, val);
        }
        return root;
    }

}
