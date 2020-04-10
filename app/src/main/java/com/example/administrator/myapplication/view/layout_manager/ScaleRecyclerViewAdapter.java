package com.example.administrator.myapplication.view.layout_manager;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.myapplication.R;

import java.util.List;

public class ScaleRecyclerViewAdapter extends RecyclerView.Adapter<ScaleRecyclerViewAdapter.RecyclerViewHolder>{


    private List<Bean> mList;

    public ScaleRecyclerViewAdapter(List<Bean> list) {
        mList = list;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new RecyclerViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.scale_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder recyclerViewHolder, int i) {
        recyclerViewHolder.itemView.setBackgroundColor(mList.get(i).getColor());
        recyclerViewHolder.mTextView.setText(mList.get(i).getContent());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView mTextView;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.scale_tv);
        }
    }
}
