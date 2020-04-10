package com.example.administrator.myapplication.view.slidingmenu;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.RelativeLayout;

import com.example.administrator.myapplication.R;

public class SlidingMenu extends HorizontalScrollView {

    private View menuView,contentView,shadowView;
    private int menuWidth;
    private Context context;
    private GestureDetector gestureDetector;
    private boolean isOpen = false;
    // 是否拦截事件
    private boolean isIntercept = false;
    private float startX, startY, offsetX, offsetY;

    public SlidingMenu(Context context) {
        this(context,null);
    }

    public SlidingMenu(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SlidingMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context,AttributeSet attributeSet){
        this.context = context;
        TypedArray array = context.obtainStyledAttributes(attributeSet, R.styleable.SlidingMenu);
        float rightMargin = array.getDimension(R.styleable.SlidingMenu_rightMargin, dip2px(50));
        array.recycle();
        menuWidth = (int) (getScreenWidth(context) - rightMargin);

        gestureDetector = new GestureDetector(
                context,new GestureDetector.SimpleOnGestureListener(){

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if (isOpen){
                    if (Math.abs(velocityX) > Math.abs(velocityY)){
                        if (velocityX < 0){
                            close();
                            return true;
                        }
                    }
                }else {
                    if (Math.abs(velocityX) > Math.abs(velocityY)){
                        if (velocityX > 0){
                            open();
                            return true;
                        }
                    }
                }
                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //1、内容页指定宽度,就是屏幕的宽度
        //2、菜单页指定宽度，就是屏幕宽度减去 - 自定义margin
        ViewGroup container = (ViewGroup)getChildAt(0);
        if (container.getChildCount() != 2){
            throw new RuntimeException("只能且必须放置两个子View!");
        }
        menuView = container.getChildAt(0);

        ViewGroup.LayoutParams menuLayoutParams = menuView.getLayoutParams();
        menuLayoutParams.width = menuWidth;
        menuView.setLayoutParams(menuLayoutParams);

        contentView = container.getChildAt(1);
        //从父布局取出内容页
        container.removeView(contentView);
        RelativeLayout outContainer = new RelativeLayout(context);//创建新容器
        outContainer.addView(contentView);//把内容页加入新容器
        shadowView = new View(context);//创建阴影层
        shadowView.setBackgroundColor(Color.parseColor("#70000000"));//阴影层设置背景颜色
        outContainer.addView(shadowView);//在新容器加入阴影层
        ViewGroup.LayoutParams contentParams = contentView.getLayoutParams();
        contentParams.width = getScreenWidth(context);
        outContainer.setLayoutParams(contentParams);//7.0以下的手机必须加这句
        container.addView(outContainer);//把新容器加入父布局
        shadowView.setAlpha(0.0f);//初始化阴影层为不透明
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN){
            //记录按下屏幕的初始位置
            startX = ev.getX();
            startY = ev.getY();
        }
        isIntercept = false;
        //处理当菜单页打开的时候，触摸内容页部分时关闭菜单页，并且拦截事件
        if (isOpen) {
            float currentX = ev.getX();
            if (currentX > menuWidth) {
                close();
                //返回true ：拦截子View的事件,但是会执行自己的onTouchEvent方法
                isIntercept = true;
                return true;
            }
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        close();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (isIntercept){
            return true;
        }
        if (gestureDetector.onTouchEvent(ev)){
            return true;
        }
        switch (ev.getAction()){
            case MotionEvent.ACTION_MOVE:
                offsetX = ev.getX() - startX;
                offsetY = ev.getY() - startY;
                //与按下屏幕的初始位置相比，当横向滑动才进行滑动
                if (Math.abs(offsetX) < Math.abs(offsetY)) {
                    return false;
                }
                break;
            case MotionEvent.ACTION_UP:
                //根据手指抬起时的滚动距离判断，要么关闭，要么打开
                int currentScrollX = getScrollX();
                if (currentScrollX > menuWidth / 2) {
                    //关闭菜单
                    close();
                } else {
                    //打开菜单
                    open();
                }
                return true;
        }
        return super.onTouchEvent(ev);
    }

    //关闭菜单
    private void close() {
        isOpen = false;
        smoothScrollTo(menuWidth, 0);
    }

    //打开菜单
    private void open() {
        isOpen = true;
        smoothScrollTo(0, 0);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        float scale = 1f * l / menuWidth;//scale从 1 - 0
        //滑动时改变内容页的阴影程度，最大1f，最小0f
        shadowView.setAlpha(1 - scale);
        //滑动时菜单页平移，抽屉效果
        menuView.setTranslationX(l * 0.55f);
    }

    //获取屏幕宽度
    private static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }


    private int dip2px(float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
