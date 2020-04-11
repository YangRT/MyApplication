package com.example.administrator.myapplication.algorithm.tree;

// 给出两个非空的二叉树 s 和 t 。判断 t 是否是 s 的一个子树
// s 的子树是指 s 的某个节点及其所有后代共同组成的一棵树。s的子树也包括s本身
public class SubtreeOfAnother {

    // 先序遍历 检查 t 先序遍历序列是否是 s 先序遍历序列的子字符串
    public boolean isSubtree2(TreeNode s, TreeNode t) {
        String tree1 = preOrder(s, true);
        String tree2 = preOrder(t, true);
        return tree1.indexOf(tree2) >= 0;
    }

    private String preOrder(TreeNode node, boolean left) {
        if (node == null) {
            if (left) return "lnull";
            else return "rnull";
        }

        return "#" + node.val + " " + preOrder(node.left, true) + " " + preOrder(node.right, false);
    }

    // 写一个判断两个树是否相等的函数 isSame
    // 再用深度优先搜索的方式遍历整个 s 树
    public boolean isSubtree(TreeNode s, TreeNode t) {
        if(s == null) return false;
        return (isSubtree(s.left, t) || isSubtree(s.right, t) ) ? true : isSame(s, t);
    }
    //isSame函数判断两个树是否相等
    public boolean isSame(TreeNode t1,TreeNode t2){
        if(t1 == null && t2 == null){
            return true;
        }
        if(t1 == null || t2 == null){
            return false;
        }
        if(t1.val != t2.val){
            return false;
        }
        return isSame(t1.left,t2.left) && isSame(t1.right,t2.right);
    }

    /*
                           _ooOoo_
                          o8888888o
                          88" . "88
                          (| -_- |)
                          O\  =  /O
                       ____/`---'\____
                     .'  \\|     |//  `.
                    /  \\|||  :  |||//  \
                   /  _||||| -:- |||||-  \
                   |   | \\\  -  /// |   |
                   | \_|  ''\---/''  |   |
                   \  .-\__  `-`  ___/-. /
                 ___`. .'  /--.--\  `. . __
              ."" '<  `.___\_<|>_/___.'  >'"".
             | | :  `- \`.;`\ _ /`;.`/ - ` : | |
             \  \ `-.   \_ __\ /__ _/   .-` /  /
        ======`-.____`-.___\_____/___.-`____.-'======
                           `=---='
        ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
                 佛祖保佑       永无BUG
        */
}
