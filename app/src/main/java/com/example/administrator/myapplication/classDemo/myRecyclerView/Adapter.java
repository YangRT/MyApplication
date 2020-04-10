package com.example.administrator.myapplication.classDemo.myRecyclerView;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.myapplication.R;

import java.util.zip.Inflater;

public class Adapter {
    LayoutInflater inflater;
    int count;
    String d;

    public Adapter(Context context,int count){
        inflater = LayoutInflater.from(context);
        this.count = count;
        Resources resources = context.getResources();
    }
    public View onCreateViewHolder(int position,View convertView,ViewGroup parent){
        convertView = inflater.inflate(R.layout.recycler_view_item,parent,false);
        TextView textView = convertView.findViewById(R.id.my_text_view);
        d = "第"+position+"行！";
        textView.setText(d);
        return convertView;
    }

    public View onBinderViewHolder(int position, View convertView, ViewGroup parent){
        TextView textView = convertView.findViewById(R.id.my_text_view);
        d = "第"+position+"行！";
        textView.setText(d);
        return convertView;
    }

    public int getViewTypeCount(){
        return 1;
    }

    public int getViewType(int position){
        return 0;
    }

    public int getCount(){
        return count;
    }

    public int getHeight(int position){
        return 150;
    }
}
