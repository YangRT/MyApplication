package com.example.administrator.myapplication.classDemo.customview;

import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

//粒子控制器（控制动画进度）
public class ExplosionAnimator extends ValueAnimator {

    public static int default_duration = 1500;
    //粒子工厂
    private ParticleFactory particleFactory;
    //粒子
    private Particle[][] mParticle;
    //field
    private View mContainer;
    private Paint mPaint;

    public ExplosionAnimator(ParticleFactory particleFactory, Bitmap bitmap, Rect bound, View mContainer) {
        this.particleFactory = particleFactory;
        this.mContainer = mContainer;
        mPaint = new Paint();
        setFloatValues(0f,1f);
        setDuration(default_duration);
        mParticle = particleFactory.generateParticles(bitmap,bound);
    }

    //绘制
    public void draw(Canvas canvas){
        if(!isStarted()){
            //动画未开始
            return;
        }
        //所有粒子开始运动
        for(Particle[] particles:mParticle){
            for(Particle particle:particles){
                particle.advance(canvas,mPaint,(Float)getAnimatedValue());
            }
        }
        mContainer.invalidate();
    }

    @Override
    public void start() {
        super.start();
        mContainer.invalidate();
    }
}
