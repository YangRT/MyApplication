package com.example.administrator.myapplication.algorithm.tree;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/*
    给定一个二叉树，返回其按层次遍历的节点值。（从左往右）

 */
public class TreeNodeOrder {

    public static List<List<Integer>> solution(TreeNode root){
        Queue<TreeNode> queue = new LinkedList<>();
        List<List<Integer>> wrapList = new LinkedList<List<Integer>>();
        if(root == null){
            return wrapList;
        }
        queue.offer(root);
        while (!queue.isEmpty()){
            int levelNum = queue.size();
            List<Integer> subList = new LinkedList<>();
            for(int i = 0;i < levelNum;i++){
                if(queue.peek().left != null){
                    queue.offer(queue.peek().left);
                }
                if(queue.peek().right != null){
                    queue.offer(queue.peek().right);
                }
                subList.add(queue.poll().val);
            }
            wrapList.add(subList);
        }
        return wrapList;
    }

    private static TreeNode createBinaryTree(Integer[] array,int index){
        TreeNode root = null;
        if(index<array.length){
            root = new TreeNode();
            Integer t = array[index];
            if(t == null){
                return null;
            }else {
                root.val = t;
            }
            root.left = createBinaryTree(array,2*index+1);
            root.right = createBinaryTree(array,2*index+2);
            return root;
        }
        return root;
    }
    public static void main(String[] args){
        Integer[] array = new Integer[]{3,9,20,null,null,15,7};
        TreeNode root = createBinaryTree(array,0);
        System.out.println(solution(root).toString());
    }
}
