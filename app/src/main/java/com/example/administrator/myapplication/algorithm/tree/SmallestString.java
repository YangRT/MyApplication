package com.example.administrator.myapplication.algorithm.tree;
// 给出一个二叉树，二叉树的每一个节点都有一个范围在0~25的数值
// 这些数值代表了从a到z的26个小写字母。值为0代表a，值为1代表b，以此类推
//寻找从叶子节点开始，在根节点结束的，字典序最小的字符串
// 一个需要注意的地方是，如果两个字符串有相同的前缀
// 字符串中任何较短的前缀在字典序上都是较小的
// 例如，在字典序上 "ab" 比 "aba" 要小
public class SmallestString {

    String ans = "{";
    public String smallestFromLeaf(TreeNode root) {
        StringBuffer buf =  new StringBuffer();
        helper(root, buf);
        return ans;
    }


    public void helper(TreeNode node, StringBuffer buf){
        if(node == null){
            return;
        }
        buf.append( (char)('a' + node.val) );
        if(node.left == null && node.right == null){
            buf.reverse();
            String s = buf.toString();
            buf.reverse();
            // compareTo函数会比较两个字符串的字典序
            // 如果返回值小于0，说明当前字符串的字典序比ans小，于是更新ans字符串
            if(s.compareTo(ans) < 0 ){
                ans = s;
            }
        }

        helper(node.left , buf );
        helper(node.right, buf );

        buf.deleteCharAt( buf.length() - 1);
    }

}
