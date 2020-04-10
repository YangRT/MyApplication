package com.example.administrator.myapplication.album;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.classDemo.bitmaploader.BitmapLoader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class GridViewAdapter extends BaseAdapter implements AbsListView.OnScrollListener {

    private static final String TAG = "GridViewAdapter";
    private int mWidth;
    private BitmapLoader bitmapLoader;
    private boolean mIsGridViewIdle = true;
    private List<String> chosePic;

    private Context mContext;
    private List<String> mList = new ArrayList<>();
    private SelectPicListener mListener;

    interface SelectPicListener{
        void selectedNum(int num);
    }

    public void setSelectedPicListener(SelectPicListener listener){
        mListener = listener;
    }
    public GridViewAdapter(Context context, List<String> list){
        mContext = context;
        mList = list;
        chosePic = new ArrayList<>();
        Resources resources = mContext.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        mWidth = dm.widthPixels;
        bitmapLoader = BitmapLoader.build(context,true);

    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if(convertView == null){
            convertView = View.inflate(mContext, R.layout.gridview_item,null);
            viewHolder = new ViewHolder();
            viewHolder.imageView = convertView.findViewById(R.id.gridview_item);
            viewHolder.box = convertView.findViewById(R.id.gridview_item_box);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked && chosePic.contains(mList.get(position))) return;
                if(isChecked && chosePic.size() == 9){
                    viewHolder.box.setChecked(false);
                    Toast.makeText(mContext,"最多选中九张照片！",Toast.LENGTH_SHORT).show();
                }else if(!isChecked && chosePic.contains(mList.get(position))){
                    checkChanged(position,isChecked);
                }else if(isChecked && !chosePic.contains(mList.get(position))){
                    checkChanged(position,isChecked);
                }

            }
        });
        //加载图片
        final String tag = (String)viewHolder.imageView.getTag();
        final String url = (String) getItem(position);
        if(!url.equals(tag)){
            viewHolder.imageView.setImageDrawable(mContext.getDrawable(R.drawable.ic_launcher_background));
        }
        if(chosePic.contains(mList.get(position))){
            viewHolder.box.setChecked(true);
        }else {
            viewHolder.box.setChecked(false);
        }
        viewHolder.imageView.setScaleType(ImageView.ScaleType.CENTER);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(mWidth/3,mWidth/3);
        viewHolder.imageView.setLayoutParams(params);
        if(mIsGridViewIdle) {
            viewHolder.imageView.setTag(url);
            bitmapLoader.bindBitmap(url, viewHolder.imageView, mWidth / 3, mWidth / 3);
        }
        return convertView;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if(scrollState == SCROLL_STATE_IDLE){
            mIsGridViewIdle = true;
            notifyDataSetChanged();
        }else {
            mIsGridViewIdle = false;
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    private class ViewHolder{
        public ImageView imageView;
        public CheckBox box;
    }

    private void checkChanged(int position,boolean status){
        if(status){
            chosePic.add(mList.get(position));
            mListener.selectedNum(chosePic.size());
        }else {
            chosePic.remove(mList.get(position));
            mListener.selectedNum(chosePic.size());
        }
    }

    public List<String> getSelectedPic(){
        return chosePic;
    }
}
