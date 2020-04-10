package com.example.administrator.myapplication.view.progressBar;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ProgressBar;

public class CircleProgressBar extends ProgressBar {

    public CircleProgressBar(Context context) {
        this(context,null);
    }

    public CircleProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CircleProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs){

    }
}
