package com.example.administrator.myapplication.classDemo.myRecyclerView;

import android.view.View;

import java.util.Stack;

public class Recycler {
    //存放不同view的实例
    private Stack<View>[] mViews;

    public Recycler(int length){
        mViews = new Stack[length];
        for(int i=0;i<length;i++){
            mViews[i] = new Stack<View>();
        }
    }
    //获取
    public View get(int viewType){
        try {
            return mViews[viewType].pop();
        }catch (Exception e){
            return null;
        }
    }
    //回收
    public void put(int viewType,View view){
        mViews[viewType].push(view);
    }
}
