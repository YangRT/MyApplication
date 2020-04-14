package com.example.administrator.myapplication.algorithm.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

// 给出一个二叉树，返回Z字形曲折遍历的结果
// 比如，在树的第一层是从左向右遍历
// 那么在第二层就要从右向左遍历
// 第三层又变为从左向右遍历。
public class ZigzagLevelOrder {
    Stack<TreeNode> stack = new Stack<>();
    List<List<Integer>> ans = new ArrayList<>();
    int dir = 0;

    public List<List<Integer>> solution(TreeNode root){
        if(root == null){
            return ans;
        }
        List<Integer> list = new ArrayList<>();
        list.add(root.val);
        stack.push(root);
        dir = 1;
        zigzagLevelOrder();
        return ans;
    }

    public void zigzagLevelOrder(){
        if (stack.empty()) return;
        List<Integer> list = new ArrayList<>();
        List<TreeNode> t = new ArrayList<>();
        while (!stack.empty()){
            TreeNode temp = stack.pop();
            if (dir == 1){
                if (temp.right != null){
                    list.add(temp.right.val);
                    t.add(temp.right);
                }
                if (temp.left != null){
                    list.add(temp.left.val);
                    t.add(temp.left);
                }

            }else {
                if (temp.left != null){
                    list.add(temp.left.val);
                    t.add(temp.left);
                }
                if (temp.right != null){
                    list.add(temp.right.val);
                    t.add(temp.right);
                }
            }
        }
        for(int i = 0;i < t.size();i++){
            if (t.get(i)==null) continue;
            stack.push(t.get(i));
        }
        ans.add(list);
        dir = dir==0?1:0;
        zigzagLevelOrder();
    }

    // 广度搜索
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> ans = new ArrayList<>();
        if(root == null){
            return ans;
        }

        Queue<TreeNode> que = new LinkedList<>();
        que.add(root);//为队列中添加根节点

        int Count = 1;//记录层数，如果是偶数层，需要对list进行反转
        while(que.isEmpty() == false){
            int n = que.size();//在开始阶段，队列中存放了当前层的所有节点
            List<Integer> res = new ArrayList<>();
            for(int i = 0; i<n ;i++){
                //遍历当前层的每一个节点，将其val值放入res，左右子节点放入队列
                TreeNode temp = que.remove();
                res.add(temp.val);

                if(temp.left != null){
                    que.add(temp.left);
                }
                if(temp.right != null){
                    que.add(temp.right);
                }
            }

            //如果是偶数层，需要对list进行反转
            if(Count % 2 == 0){
                Collections.reverse(res);
            }
            ans.add(res);
            Count++;
        }
        return ans;
    }

    // 深搜
    public List<List<Integer>> zigzagLevelOrder2(TreeNode root) {
        List<List<Integer>> ans = new ArrayList<>();
        if(root == null){
            return ans;
        }
        dfs(root, 1, ans);
        return ans;
    }
    public void dfs(TreeNode node, int level, List<List<Integer>> ans ){
        if(node == null){
            return;
        }

        if(level > ans.size()){
            ans.add(new ArrayList<Integer>());
        }
        if(level % 2 == 0){
            ans.get(level-1).add(0,node.val);
        }else{
            ans.get(level-1).add(node.val);
        }

        dfs(node.left , level+1 ,ans);
        dfs(node.right, level+1 ,ans);
    }


}
