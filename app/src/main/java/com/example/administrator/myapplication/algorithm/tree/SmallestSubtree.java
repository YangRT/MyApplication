package com.example.administrator.myapplication.algorithm.tree;

// 给出一个二叉树，root 是它的根节点，每个节点的深度是指到达 root 的最短距离
//现在有两个定义：
//1 最深的节点：
//如果某个节点的深度比该树的其他节点都大，那么我们称之为最深的节点。
//2 子树：
//一个节点的子树是指该节点的所有后代节点和该节点本身
// 返回能满足“以该结点为根的子树中包含所有最深的结点”这一条件的具有最大深度的结点
public class SmallestSubtree {

    public TreeNode subtreeWithAllDeepest(TreeNode root) {
        return dfs(root).node;
    }

    // 如果只有一个 childResult 具有最深节点，返回 childResult.node
    //如果两个孩子都有最深节点，返回 node
    public Result dfs(TreeNode node) {
        if (node == null) return new Result(null, 0);
        Result L = dfs(node.left),
                R = dfs(node.right);
        if (L.dist > R.dist) return new Result(L.node, L.dist + 1);
        if (L.dist < R.dist) return new Result(R.node, R.dist + 1);
        return new Result(node, L.dist + 1);
    }

    class Result {
        TreeNode node;
        int dist;
        Result(TreeNode n, int d) {
            node = n;
            dist = d;
        }
    }
}


