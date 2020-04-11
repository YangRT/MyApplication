package com.example.administrator.myapplication.algorithm.tree;

import java.util.ArrayList;
import java.util.List;

// 给出一个二叉搜索树，使用中序遍历的方法，对其进行重新排列
// 使得原树的最左的节点，变成新树的根节点
// 新树中，每一个节点只有右节点，没有左节点
public class IncreasingOrderSearch {

    // 先中序遍历整个树，将结果保存在一个List中
    // 之后再遍历这个List，将所有的节点值都存入一个新树中
    public TreeNode increasingBST(TreeNode root) {
        List<Integer> k = new ArrayList<>();
        buildTree( root, k);

        TreeNode pre = new TreeNode(0);
        TreeNode temp = pre;
        for(int i : k){
            temp.right = new TreeNode(i);
            temp = temp.right;
        }
        return pre.right;
    }
    public void buildTree(TreeNode root, List<Integer> k){
        if(root == null){
            return;
        }
        buildTree(root.left , k);
        k.add(root.val);
        buildTree(root.right , k);
    }

    // 中序遍历+直接改变原来的树
    //当遍历完一个节点的左子树后，就削去它的左子树，直接把这个节点作为一个新的节点放入树中
    TreeNode pre = new TreeNode(0);
    TreeNode temp = pre;

    public TreeNode increasingBST2(TreeNode root) {
        buildTree( root);
        return pre.right;
    }
    public void buildTree(TreeNode root){
        if(root == null){
            return;
        }
        buildTree(root.left);
        root.left = null;
        temp.right = root;
        temp = temp.right;
        buildTree(root.right);
    }

}
