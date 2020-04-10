package com.example.administrator.myapplication.classDemo.bitmaploader;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.administrator.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class BitmapAdapter extends BaseAdapter {
    List<String> mUrlList = new ArrayList<>();
    LayoutInflater layoutInflater;
    Drawable drawable;
    BitmapLoader bitmapLoader;

    BitmapAdapter(Context context,List<String> list){
        layoutInflater = LayoutInflater.from(context);
        mUrlList = list;
        bitmapLoader = BitmapLoader.build(context,false);
        drawable = context.getDrawable(R.drawable.ic_launcher_background);
    }

    @Override
    public int getCount() {
        return mUrlList.size();
    }

    @Override
    public Object getItem(int position) {
        return mUrlList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.gridview_item,parent,false);
            holder = new ViewHolder();
            holder.view = (ImageView)convertView.findViewById(R.id.gridview_item);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        ImageView imageView = holder.view;
        final String tag = (String)imageView.getTag();
        final String url = (String) getItem(position);
        if(!url.equals(tag)){
            imageView.setImageDrawable(drawable);
        }
        imageView.setTag(url);
        bitmapLoader.bindBitmap(url,imageView,imageView.getWidth(),imageView.getHeight());
        return convertView;
    }

    class ViewHolder{
        ImageView view;
    }
}
