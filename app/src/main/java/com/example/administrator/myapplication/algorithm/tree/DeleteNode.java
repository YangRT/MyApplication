package com.example.administrator.myapplication.algorithm.tree;

// 给定一个二叉搜索树的根节点 root 和一个值 key
// 删除二叉搜索树中的 key 对应的节点，并保证二叉搜索树的性质不变
// 返回二叉搜索树（有可能被更新）的根节点的引用
// 要求算法时间复杂度为 O(h)，h 为树的高度
public class DeleteNode {

    // 找后继节点
    public int successor(TreeNode root) {
        root = root.right;
        while (root.left != null) root = root.left;
        return root.val;
    }


    // 找前继节点
    public int predecessor(TreeNode root) {
        root = root.left;
        while (root.right != null) root = root.right;
        return root.val;
    }

    public TreeNode deleteNode(TreeNode root, int key) {
        if (root == null) return null;

        // 在右子树
        if (key > root.val) root.right = deleteNode(root.right, key);
            // 在左子树
        else if (key < root.val) root.left = deleteNode(root.left, key);
        else {
            // 叶子节点,
            if (root.left == null && root.right == null) root = null;
                // 非叶子节点, 有右节点
            else if (root.right != null) {
                root.val = successor(root);
                root.right = deleteNode(root.right, root.val);
            }
            // 非叶子节点, 没有右节点,有左节点
            else {
                root.val = predecessor(root);
                root.left = deleteNode(root.left, root.val);
            }
        }
        return root;
    }


}
