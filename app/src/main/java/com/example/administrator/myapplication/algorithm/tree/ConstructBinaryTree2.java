package com.example.administrator.myapplication.algorithm.tree;

// 给出一个树的中序遍历和后序遍历顺序，构造一棵二叉树
// 你可以假设，树中没有重复的节点
public class ConstructBinaryTree2 {

    public TreeNode buildTree(int[] inorder, int[] postorder) {
        return helper(inorder,0, inorder.length-1 , postorder, 0, postorder.length-1);
    }

    public TreeNode helper(int[] inorder , int in_start, int in_end, int[] postorder, int po_start, int po_end ){
        if(in_start > in_end || po_start > po_end){
            return null;
        }
        int rootVal = postorder[po_end];//后序遍历的最后一个值必定是根节点

        //根据根节点的值，在中序遍历顺序中找到根节点的下标，存放于rootIndex
        int rootIndex = 0;
        for(int i = in_end; i >= in_start ; i--){
            if(inorder[i] == rootVal){
                rootIndex = i;
                break;
            }
        }

        //在中序遍历数组中，计算左子树的节点个数
        int length = rootIndex - in_start;

        TreeNode root = new TreeNode(rootVal);
        root.left = helper(inorder, in_start, rootIndex-1, postorder, po_start, po_start+length-1);
        //左子树的区间，在中序遍历的数组中，是[in_start, rootIndex-1]，
        //在后序遍历的数组中，是[po_start, po_start+length-1]

        root.right= helper(inorder, rootIndex+1, in_end, postorder, po_start+length,po_end-1);
        //右子树的区间，在中序遍历的数组中，是[rootIndex+1, in_end]，
        //在后序遍历的数组中，是[po_start+length, po_end-1]

        return root;
    }

}
