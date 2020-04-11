package com.example.administrator.myapplication.algorithm.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// 给定二叉树，按垂序遍历返回其结点值
// 位于 (X, Y) 的每个结点，其左右子结点分别位于 (X-1, Y-1) 和 (X+1, Y-1)
// 把一条垂线从 X = -infinity 移动到 X = +infinity
// 每当该垂线与结点接触时，我们按从上到下的顺序报告结点的值（ Y 坐标递减）
// 如果两个结点位置相同，则首先报告的结点值较小
//按 X 坐标顺序返回非空报告的列表。每个报告都有一个结点值列表
public class VerticalTraversal {
    List<Location> l;

    class Location implements Comparable<Location>{
        int x , y , val;
        Location(int x, int y ,int val){
            this.x = x;
            this.y = y;
            this.val = val;
        }
        public int compareTo(Location that){
            if(this.x != that.x){
                return Integer.compare(this.x , that.x);
                //this.x 较大时，返回1， 较小时，返回-1
                //compare(x,y)函数的源码:
                //return (x < y) ? -1 : ((x == y) ? 0 : 1);
            }else if (this.y != that.y){
                //y是降序排列的，和x相反，因此
                //this.y 较大时，返回-1， 较小时，返回1,和this.x的正好相反
                return (this.y > that.y) ? -1: ((this.y == that.y) ? 0 : 1);
            }else{
                return Integer.compare(this.val , that.val);
            }
        }
    }

    public void setXY(TreeNode root, int x, int y){
        if(root == null){
            return;
        }
        l.add( new Location( x , y , root.val));
        setXY(root.left , x-1 , y-1);
        setXY(root.right, x+1 , y-1);
    }

    public List<List<Integer>> verticalTraversal(TreeNode root) {
        l = new ArrayList();
        setXY(root, 0, 0);//遍历所有节点，设定X、Y的值
        Collections.sort(l);//对所有Location进行排序

        List<List<Integer>> ans = new ArrayList();
        ans.add(new ArrayList<Integer>());//为了保证ans非空
        int prev = l.get(0).x;//设定为排在第一位的location的x值

        for(Location i : l){
            //如果x的值不同了，就应该新建一个List
            if(i.x != prev){
                prev = i.x;
                ans.add(new ArrayList<Integer>() );
            }
            //需要在ans最后一个list中添加当前Location的val值
            ans.get(ans.size() - 1).add( i.val );
        }
        return ans;
    }

}
