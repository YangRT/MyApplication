package com.example.administrator.myapplication.algorithm.tree;

import java.util.LinkedList;
import java.util.Queue;

// 给定一个二叉树，检查是否是中心对称的
public class SymmetricTree {

    // 递归
    public boolean symmetricTree(TreeNode root){
        if (root == null) return true;
        return check(root.left,root.right);
    }

    public boolean check(TreeNode left,TreeNode right){
        if (left == null && right == null) return true;
        if (left == null || right == null) return false;
        if (left.val == right.val){
            return check(left.left,right.right) && check(left.right,right.left);
        }else {
            return false;
        }
    }

    // 迭代 广度搜索 借用队列存储
    public boolean isSymmetric(TreeNode root) {
        if(root == null){
            return true;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        queue.add(root);
        while(queue.isEmpty() == false){
            TreeNode t1 = queue.poll();
            TreeNode t2 = queue.poll();
            if(t1 == null && t2 == null){
                continue;
            }
            if(t1 != null && t2 == null){
                return false;
            }
            if(t1 == null && t2 != null){
                return false;
            }
            if(t1.val != t2.val){
                return false;
            }
            queue.add(t1.right);
            queue.add(t2.left);

            queue.add(t1.left);
            queue.add(t2.right);
        }
        return true;
    }

}
