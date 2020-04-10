package com.example.administrator.myapplication.view.slipToDelete;

import android.content.ClipData;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ItemTouchCallback<T> extends android.support.v7.widget.helper.ItemTouchHelper.Callback {

    private List<T> mItems = new ArrayList<>();
    private RecyclerView.Adapter mAdapter;

    public ItemTouchCallback(RecyclerView.Adapter adapter,List<T> items){
        mAdapter = adapter;
        mItems = items;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        // 获取触摸响应的方向
        // 包含两个 1.拖动dragFlags 2.侧滑删除swipeFlags
        // 代表只能是向左侧滑删除
        // 当前可以是这样ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT
        int swipeFlag = ItemTouchHelper.LEFT;

        int dragFlag = 0;
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            // GridView 样式四个方向都可以
            dragFlag = ItemTouchHelper.UP | ItemTouchHelper.LEFT |
                    ItemTouchHelper.DOWN | ItemTouchHelper.RIGHT;
        } else if (recyclerView.getLayoutManager() instanceof LinearLayoutManager){
            if(((LinearLayoutManager)(recyclerView.getLayoutManager())).getOrientation() == LinearLayoutManager.HORIZONTAL){
                dragFlag = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            } else {
                dragFlag = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            }
        }
        return makeMovementFlags(dragFlag, swipeFlag);

    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
        //拖动的时候不断的回调方法

        // 获取原来的位置
       int fromPosition = viewHolder.getAdapterPosition();
       // 得到目标的位置
        int targetPosition = viewHolder1.getAdapterPosition();
        Log.e("TouchCallBack","from:"+fromPosition+",target:"+targetPosition);
        if(fromPosition > targetPosition){
            for (int i = fromPosition; i > targetPosition; i--) {
                Collections.swap(mItems, i, i - 1);// 改变实际的数据集
                Log.e("TouchCallBack","i:"+i);
            }
        }else{
            for (int i = fromPosition; i < targetPosition; i++) {
                Collections.swap(mItems, i, i + 1);// 改变实际的数据集
            }
        }
        mAdapter.notifyItemMoved(fromPosition, targetPosition);
        Log.e("TouchCallBack",fromPosition+":"+mItems.get(fromPosition)+","+targetPosition+":"+mItems.get(targetPosition));
        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        // 获取当前删除的位置
        int position = viewHolder.getAdapterPosition();
        mItems.remove(position);
        // adapter 更新notify当前位置删除
        mAdapter.notifyItemRemoved(position);
    }

    @Override
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
        //拖动选择状态改变回调
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            // ItemTouchHelper.ACTION_STATE_IDLE 看看源码解释就能理解了
            // 侧滑或者拖动的时候背景设置为灰色
            viewHolder.itemView.setBackgroundColor(Color.GRAY);
        }
    }

    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        // 回到正常状态的时候回调
        // 正常默认状态下背景恢复默认
        viewHolder.itemView.setBackgroundColor(0);
        viewHolder.itemView.setTranslationX(0);
    }
}
