package com.example.administrator.myapplication.algorithm.tree;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 给出一个二叉树，返回所有重复的子树
// 结构和节点的value值都相同的子树就是相同的子树
// 对于每一种重复的子树，你只需要返回一个该类子树的根节点即可
public class FindDuplicateSubtrees {

    Map<String, Integer> count;
    List<TreeNode> ans;
    // 序列化二叉树  使用深度优先搜索，其中递归函数返回当前子树的序列化结果
    // 每个节点开始的子树序列化结果保存在 map 判断是否存在重复的子树
    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
        count = new HashMap();
        ans = new ArrayList();
        collect(root);
        return ans;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public String collect(TreeNode node) {
        if (node == null) return "#";
        String serial = node.val + "," + collect(node.left) + "," + collect(node.right);
        count.put(serial, count.getOrDefault(serial, 0) + 1);
        if (count.get(serial) == 2)
            ans.add(node);
        return serial;
    }

}
