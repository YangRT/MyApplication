package com.example.administrator.myapplication.classDemo.customview;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

//粒子特效场地
public class ExplosionFiled extends View {
    //可以同时执行多个动画
    private ArrayList<ExplosionAnimator> explosionAnimators;
    //可以实现不同的粒子效果
    private ParticleFactory particleFactory;
    //给控件设置点击事件
    private OnClickListener onClickListener;


    public ExplosionFiled(Context context,ParticleFactory particleFactory) {
        super(context);
        this.particleFactory = particleFactory;
        explosionAnimators = new ArrayList<>();
        //将动画区域绑定到activity
        attach2Activity();
    }

    private void attach2Activity() {
        ViewGroup viewGroup = (ViewGroup)((Activity)getContext()).getWindow().getDecorView();
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        viewGroup.addView(this);
    }

    /**
     * @param view 需要实现粒子效果的view
     */
    public void addListener(View view){
        if(view instanceof ViewGroup){
            ViewGroup viewGroup = (ViewGroup)view;
            int childCount = viewGroup.getChildCount();
            for(int i = 0;i < childCount;i++){
                addListener(viewGroup.getChildAt(i));
            }
        }else {
            view.setClickable(true);
            view.setOnClickListener(getOnClickListener());
        }
    }

    private OnClickListener getOnClickListener() {
        if(onClickListener == null){
            onClickListener = new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //开启粒子爆炸效果
                    ExplosionFiled.this.explose(v);
                }
            };
        }
        return onClickListener;
    }

    //动画开始的地方(主入口)
    private void explose(final View view) {
        //获取控件位置
        //保存了控件位置大小
        final Rect rect = new Rect();
        view.getGlobalVisibleRect(rect);
        if(rect.width() == 0 || rect.height() == 0){
            return;
        }
        //开始动画效果
        //抖动缩小
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0,1f);
        valueAnimator.setDuration(150);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                view.setTranslationX((Utils.RANDOM.nextFloat() - 0.5f)*view.getWidth()*0.05f);
                view.setTranslationY((Utils.RANDOM.nextFloat() - 0.5f)*view.getHeight()*0.05f);
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //动画结束
                //粒子效果
                explorer(view,rect);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        valueAnimator.start();
        //控件复原
    }

    private void explorer(final View view, Rect rect) {
        //获取动画控制器
        final ExplosionAnimator animator = new ExplosionAnimator(particleFactory,Utils.createBitmapFromView(view),rect,this);
        explosionAnimators.add(animator);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                view.setClickable(false);
                //缩小
                view.animate().setDuration(150).scaleX(0f).scaleY(0f).start();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                view.setClickable(true);
                view.animate().setDuration(150).scaleX(1f).scaleY(1f).start();
                //将动画从集合中移除
                explosionAnimators.remove(animator);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //开启所有动画
        for(ExplosionAnimator animator:explosionAnimators){
            animator.draw(canvas);
        }
    }
}
