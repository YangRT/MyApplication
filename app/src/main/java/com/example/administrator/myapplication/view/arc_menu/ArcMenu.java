package com.example.administrator.myapplication.view.arc_menu;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;

import com.example.administrator.myapplication.R;

public class ArcMenu extends ViewGroup implements View.OnClickListener{

    private static final int POS_LEFT_TOP = 0;
    private static final int POS_LEFT_BOTTOM = 1;
    private static final int POS_RIGHT_TOP = 2;
    private static final int POS_RIGHT_BOTTOM = 3;


    // 展开半径
    private int radius;
    // 位置
    private Position position = Position.RIGHT_BOTTOM;

    private Status status = Status.CLOSE;

    private View mainButton;
    private OnMenuItemClickListener onMenuItemClickListener;


    // 菜单位置
    public enum Position{
        LEFT_TOP,LEFT_BOTTOM,RIGHT_TOP,RIGHT_BOTTOM
    }

    public enum Status{
        OPEN,CLOSE
    }

    public interface OnMenuItemClickListener{
        void onClick(View view,int position);
    }

    public ArcMenu(Context context) {
        this(context,null);
    }

    public ArcMenu(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ArcMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs,defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr){
        radius = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,100,getResources().getDisplayMetrics());
        TypedArray array = context.obtainStyledAttributes(attrs,R.styleable.ArcMenu,defStyleAttr,0);
        int pos = array.getInt(R.styleable.ArcMenu_position,POS_RIGHT_BOTTOM);
        switch (pos){
            case POS_LEFT_TOP:
                position = Position.LEFT_TOP;
                break;
            case POS_LEFT_BOTTOM:
                position = Position.LEFT_BOTTOM;
                break;
            case POS_RIGHT_TOP:
                position = Position.RIGHT_TOP;
                break;
            case POS_RIGHT_BOTTOM:
                position = Position.RIGHT_BOTTOM;
                break;
        }
        radius = (int)array.getDimension(R.styleable.ArcMenu_radius,TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,100,getResources().getDisplayMetrics()));
        array.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int count = getChildCount();
        for (int i = 0;i < count;i++){
            measureChild(getChildAt(i),widthMeasureSpec,heightMeasureSpec);
        }
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed){
            layoutMainButton();
            int count = getChildCount();
            for (int i= 0;i < count-1;i++){
                View child = getChildAt(i+1);
                child.setVisibility(GONE);
                int cl = (int) (radius * Math.sin(Math.PI/2/(count-2)*i));
                int ct = (int)(radius * Math.cos(Math.PI/2/(count-2)*i));
                int cWidth = child.getMeasuredWidth();
                int cHeight = child.getMeasuredHeight();
                // 底部
                if (position == Position.LEFT_BOTTOM || position == Position.RIGHT_BOTTOM){
                    ct = getMeasuredHeight()- cHeight -ct;
                }
                // 右边
                if (position == Position.RIGHT_TOP|| position == Position.RIGHT_BOTTOM){
                    cl = getMeasuredWidth()- cWidth -cl;
                }
                child.layout(cl,ct,cl+cWidth,ct+cHeight);
            }
        }
    }


    private void layoutMainButton(){
        mainButton = getChildAt(0);
        mainButton.setOnClickListener(this);
        int left,top;
        int width = mainButton.getMeasuredWidth();
        int height = mainButton.getMeasuredHeight();
        switch (position){
            case LEFT_BOTTOM:
                left = 0;
                top = getMeasuredHeight() - height;
                break;
            case RIGHT_TOP:
                left = getMeasuredWidth() - width;
                top = 0;
                break;
            case RIGHT_BOTTOM:
                left = getMeasuredWidth() - width;
                top = getMeasuredHeight() - height;
                break;
            default:
                left = 0;
                top = 0;
        }
        mainButton.layout(left,top,left+width,top+height);
    }

    @Override
    public void onClick(View v) {
        rotateMainButton(v,0f,360f,300);
        toggleMenu(300);
    }

    public void toggleMenu(int duration){
        int count = getChildCount();
        for (int i = 0;i < count-1;i++){
            final View child = getChildAt(i+1);
            child.setVisibility(VISIBLE);
            int cl = (int) (radius * Math.sin(Math.PI/2/(count-2)*i));
            int ct = (int)(radius * Math.cos(Math.PI/2/(count-2)*i));
            int xFlag = 1;
            int yFlag = 1;
            if (position == Position.LEFT_TOP || position == Position.LEFT_BOTTOM){
                xFlag = -1;
            }
            if (position == Position.LEFT_TOP || position == Position.RIGHT_TOP){
                yFlag = -1;
            }
            AnimationSet animationSet = new AnimationSet(true);
            TranslateAnimation translateAnimation= null;
            if (status == Status.CLOSE){
                translateAnimation = new TranslateAnimation(xFlag*cl,0,yFlag*ct,0);
                child.setClickable(true);
                child.setFocusable(true);
            }else {
                translateAnimation = new TranslateAnimation(0,xFlag*cl,0,yFlag*ct);
                child.setClickable(false);
                child.setFocusable(false);
            }
            translateAnimation.setFillAfter(true);
            translateAnimation.setDuration(duration);
            translateAnimation.setStartOffset((i*100)/count);
            translateAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    if (status == Status.CLOSE){
                        child.setVisibility(GONE);
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            RotateAnimation rotateAnimation = new RotateAnimation(0,720, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
            rotateAnimation.setDuration(duration);
            rotateAnimation.setFillAfter(true);
            animationSet.addAnimation(rotateAnimation);
            animationSet.addAnimation(translateAnimation);
            child.startAnimation(animationSet);
            final int pos = i+1;
            child.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onMenuItemClickListener != null){
                        onMenuItemClickListener.onClick(v,pos);
                        menuItemAnim(pos-1);
                        changeMenuStatus();
                    }
                }
            });
        }
        // 切换菜单状态
        changeMenuStatus();
    }

    private void menuItemAnim(int pos){
        int count = getChildCount();
        for(int i = 0;i <count-1;i++){
            View v = getChildAt(i+1);
            if (i == pos){
                v.startAnimation(scaleBigAnimation(300));
            }else {
                v.startAnimation(scaleSmallAnimation(300));

            }
            v.setClickable(false);
            v.setFocusable(false);
        }

    }

    private void changeMenuStatus(){
        status = (status == Status.CLOSE ? Status.OPEN : Status.CLOSE);
    }

    private Animation scaleBigAnimation(int duration){
        AnimationSet set = new AnimationSet(true);
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f,4.0f,1.f,4.0f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1f,0f);
        set.addAnimation(scaleAnimation);
        set.addAnimation(alphaAnimation);
        set.setDuration(duration);
        set.setFillAfter(true);
        return set;
    }

    private Animation scaleSmallAnimation(int duration){
        AnimationSet set = new AnimationSet(true);
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f,0f,1.f,0f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1f,0f);
        set.addAnimation(scaleAnimation);
        set.addAnimation(alphaAnimation);
        set.setDuration(duration);
        set.setFillAfter(true);
        return set;
    }

    private void rotateMainButton(View view,float start,float end,int duration){
        RotateAnimation rotateAnimation = new RotateAnimation(start,end, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        rotateAnimation.setDuration(duration);
        rotateAnimation.setFillAfter(true);
        view.startAnimation(rotateAnimation);
    }

    public void setOnMenuItemClickListener(OnMenuItemClickListener listener){
        onMenuItemClickListener = listener;
    }
}
