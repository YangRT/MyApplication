package com.example.administrator.myapplication.view.channelView;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class ChannelView extends ScrollView {
    private Context mContext;


    /**
     * 列数
     */
    private int channelColumn = 4;
    private int channelWidth;
    private int channelHeight;

    /**
     * 频道板块title高度
     */
    private int channelTitleHeight;
    /**
     * 频道板块title左右padding
     */
    private int platesTitleLeftRightPadding;

    /**
     * 水平方向上的间隔线
     */
    private int channelHorizontalSpacing;

    private int channelPadding;
    public ChannelView(Context context){
        this(context,null);
    }

    public ChannelView(Context context, AttributeSet set){
        this(context,set,0);
    }

    public ChannelView(Context context,AttributeSet set,int defStyleAttr){
        super(context,set,defStyleAttr);
        this.mContext = context;
    }
}
