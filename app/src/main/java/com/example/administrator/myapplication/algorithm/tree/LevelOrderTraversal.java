package com.example.administrator.myapplication.algorithm.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// 给出一个二叉树，返回自底向上逐层遍历的结果
// 每层遍历的顺序都是从左向右遍历
public class LevelOrderTraversal {

    List<List<Integer>> ans;
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        ans = new ArrayList<>();
        if(root == null){
            return ans;
        }

        dfs(root, 1);
        Collections.reverse(ans);
        return ans;
    }

    public void dfs(TreeNode node, int level){
        if(node == null){
            return;
        }
        if(level > ans.size() ){
            ans.add(new ArrayList<Integer>());
        }
        ans.get(level-1).add(node.val);
        dfs(node.left, level+1 );
        dfs(node.right, level+1 );
    }

}
