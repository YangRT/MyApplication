package com.example.administrator.myapplication.album;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.classDemo.bitmaploader.BitmapLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private Context mContext;
    private List<PictureType> mList = new ArrayList<>();
    private BitmapLoader bitmapLoader;
    private ChoiseListener mListener;
    private int mWidth;

    interface ChoiseListener{
        void choseChanged(int position);
    }

    public void setListener(ChoiseListener listener){
        mListener = listener;
    }

    public RecyclerViewAdapter(Context context,List<PictureType> list){
        mContext = context;
        mList = list;
        Resources resources = mContext.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        mWidth = dm.widthPixels;
        bitmapLoader = BitmapLoader.build(mContext,true);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_view_item_type,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final int position = i;
        viewHolder.tvType.setText(mList.get(i).getType());
        viewHolder.tvCount.setText(new StringBuilder().append("(").append(mList.get(i).getNum()).append(")").toString());
        if(!mList.get(i).getChose()){
            viewHolder.imageChoiced.setVisibility(View.INVISIBLE);
        }
        final String tag = (String)viewHolder.imageType.getTag();
        final String url = mList.get(i).getPath();
        if(!url.equals(tag)){
            viewHolder.imageType.setImageDrawable(mContext.getDrawable(R.drawable.ic_launcher_background));
        }
        viewHolder.imageType.setTag(mList.get(i).getPath());
        bitmapLoader.bindBitmap(url, viewHolder.imageType,mWidth/5,mWidth/5);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.imageChoiced.setVisibility(View.VISIBLE);
                mListener.choseChanged(position);

            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvType;
        TextView tvCount;
        ImageView imageType;
        ImageView imageChoiced;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvType = itemView.findViewById(R.id.tv_type_item);
            tvCount = itemView.findViewById(R.id.tv_type_item2);
            imageType = itemView.findViewById(R.id.image_type_item);
            imageChoiced = itemView.findViewById(R.id.image_choice_type);
        }
    }
}
