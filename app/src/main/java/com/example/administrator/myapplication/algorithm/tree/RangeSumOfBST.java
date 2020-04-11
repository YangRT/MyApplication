package com.example.administrator.myapplication.algorithm.tree;

// 给定二叉搜索树的根结点 root
// 返回 L 和 R（含）之间的所有结点的值的和

public class RangeSumOfBST {

    public int rangeSumBST(TreeNode root, int L, int R) {
        if(root == null){
            return 0;
        }
        if( root.val >= L && root.val <= R){
            return rangeSumBST(root.left,L ,R) + rangeSumBST(root.right ,L ,R) + root.val;
        }else{
            return rangeSumBST(root.left,L ,R) + rangeSumBST(root.right ,L ,R);
        }
    }

    public int rangeSumBST2(TreeNode root, int L, int R) {
        if(root == null){
            return 0;
        }
        if( root.val >= L && root.val <= R){
            return rangeSumBST2(root.left,L ,R) + rangeSumBST2(root.right ,L ,R) + root.val;
        }else if(root.val > R){
            //如果根节点大于R，则全部右子树节点都大于R，只需考虑左子树即可
            return rangeSumBST2(root.left,L ,R);
        }else {
            //这里是(root.val < L)的情况
            //如果根节点小于L，则全部左子树节点都小于L，只需考虑右子树即可
            return   rangeSumBST2(root.right ,L ,R);
        }
    }


}
