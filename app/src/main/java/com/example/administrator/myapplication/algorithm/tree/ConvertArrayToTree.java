package com.example.administrator.myapplication.algorithm.tree;

// 给出升序排列的数组，将其转化成一棵平衡二叉搜索树
// 该树每一个节点的左右子树高度相差不超过1
public class ConvertArrayToTree {
    public TreeNode sortedArrayToBST(int[] nums) {
        return helper(0, nums.length -1, nums);
    }

    public TreeNode helper(int l , int r, int[] nums){
        if(l > r){
            return null;
        }
        int mid = (l + r)/2;
        TreeNode node = new TreeNode(nums[mid]);
        node.left = helper(l , mid-1, nums);
        node.right = helper(mid+1 , r, nums);
        return node;
    }

}
