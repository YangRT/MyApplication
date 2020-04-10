package com.example.administrator.myapplication.view.slipToDelete;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Scroller;

import com.example.administrator.myapplication.R;

//简单实现侧滑删除
public class MyRecyclerView extends RecyclerView implements GestureDetector.OnGestureListener,View.OnTouchListener{
    private Context mContext;
    private View view;
    private MotionEvent m;
    private Boolean isShow = false;
    private View deleteButton;
    private GestureDetector gestureDetector;
    private ViewGroup itemLayout;
    private ViewHolder viewHolder;

    @Override
    public boolean onDown(MotionEvent e) {
        m = e;
        if (!isShow){
            view = findChildViewUnder(e.getX(),e.getY());
            if(view != null){
            viewHolder = getChildViewHolder(view);}
        }
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        if (!isShow){
            view = findChildViewUnder(e.getX(),e.getY());
            if(view != null){
                viewHolder = getChildViewHolder(view);}
        }
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if(!isShow && Math.abs(velocityX) > Math.abs(velocityY)){
            Log.e("my","begin");
            if(e1 == null){
                e1 = m;
            }
            view = findChildViewUnder(e1.getX(),e1.getY());
            viewHolder = getChildViewHolder(view);
            deleteButton = LayoutInflater.from(mContext).inflate(R.layout.delete_button,null);
            deleteButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    mListener.onDeleteClick(viewHolder.getAdapterPosition());
                    itemLayout.removeView(deleteButton);
                    deleteButton = null;
                    isShow = false;
                }
            });
            itemLayout = (ViewGroup) view;
            viewHolder = getChildViewHolder(itemLayout);

            itemLayout.addView(deleteButton,150,150);
            isShow = true;
            itemLayout.scrollBy(156,0);
        }


        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(isShow){
            itemLayout.removeView(deleteButton);
            itemLayout.scrollTo(0,0);
            isShow = false;
            deleteButton = null;
            return false;
        }else{
            return gestureDetector.onTouchEvent(event);
        }
    }


    //各种状态
    private enum Status{
        CLOSE,CLOSING,OPENING,OPEN
    }
    private Status status = Status.CLOSE;

    private Scroller mScroller;
    private VelocityTracker mTracker; //检测滑动速度
    private static final int VELOCITY = 100;  //滑动临界值
    private static final int DEFAULT_TIME = 200; //默认时间
    private boolean isScroll;
    private boolean isHorMoving;
    private boolean isVerMoving;



    private int mPosition;
    //触摸点
    private int lastX;
    private int lastY;

    private onItemClickListener mListener;

    public MyRecyclerView(@NonNull Context context) {
        this(context,null);
    }

    public MyRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        mScroller = new Scroller(mContext,new LinearInterpolator());
        mTracker = VelocityTracker.obtain();
        gestureDetector = new GestureDetector(mContext,this);
        setOnTouchListener(this);

    }



public void setListener(onItemClickListener clickListener){
        mListener = clickListener;
}
}
