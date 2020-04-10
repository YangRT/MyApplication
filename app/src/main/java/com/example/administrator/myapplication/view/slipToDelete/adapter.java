package com.example.administrator.myapplication.view.slipToDelete;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.administrator.myapplication.R;

public class adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    public adapter(Context context){
        this.context = context;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_view_item,viewGroup,false);
        return new myViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 30;
    }

    static  class myViewHolder extends RecyclerView.ViewHolder{

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
