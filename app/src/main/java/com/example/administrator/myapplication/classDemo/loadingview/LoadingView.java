package com.example.administrator.myapplication.classDemo.loadingview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

import com.example.administrator.myapplication.R;

public class LoadingView extends SurfaceView implements SurfaceHolder.Callback,Runnable{

   private enum LoadingStatus{
       DOWN,UP,FREE
   }
   private LoadingStatus loadingStatus = LoadingStatus.DOWN;
   private int ballColor;
   private int ballRadius;
   private int lineColor;
   private int lineWidth; //连线宽度
   private int strokeWidth;
   private float downDistance=0; //水平位置下降距离
   private float maxDownDistance; //水平位置最大下降距离（最低点）
   private float  upDistance=0;
   private float maxUpDistance;
   private float freeDownDistance=0;
   private float maxFreeDownDistance;
   private ValueAnimator downControl;
   private ValueAnimator upControl;
   private ValueAnimator freeDownControl;
   private AnimatorSet animatorSet;
   private boolean isAnimationShowing;
   private SurfaceHolder holder;
   private Canvas canvas;
   private Paint paint;
   private Path path;
   private boolean isRunning; //标记新线程是否在运行

    public LoadingView(Context context) {
        this(context,null);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //获取自定义属性
        initAttr(context,attrs);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(strokeWidth);
        path = new Path();
        holder = getHolder();
        //getSurfaceHolder添加回调
        holder.addCallback(this);
        //初始化距离控制
        initControl();
    }

    private void initControl() {
        downControl = ValueAnimator.ofFloat(0,maxDownDistance);
        downControl.setDuration(500);
        downControl.setInterpolator(new DecelerateInterpolator());
        downControl.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                downDistance = (float)animation.getAnimatedValue();
            }
        });
        downControl.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                loadingStatus = LoadingStatus.DOWN;
                isAnimationShowing = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        });
        upControl = ValueAnimator.ofFloat(0,maxDownDistance);
        upControl.setDuration(500);
        upControl.setInterpolator(new ShockInterpolator());
        upControl.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                upDistance = (float)animation.getAnimatedValue();
                if(upDistance >= maxDownDistance && freeDownControl != null && !freeDownControl.isRunning()
                && !freeDownControl.isStarted()){
                    freeDownControl.start();
                }
                //震荡绳子位置调整，绳子不能超过小球底部
                if(upDistance - maxDownDistance >= freeDownDistance){
                    upDistance = maxDownDistance + freeDownDistance;
                }
            }
        });
        upControl.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                loadingStatus = LoadingStatus.UP;
                isAnimationShowing = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        });
        freeDownControl = ValueAnimator.ofFloat(0,(float)(2*Math.sqrt(maxFreeDownDistance/5)));
        freeDownControl.setDuration(700);
        freeDownControl.setInterpolator(new AccelerateInterpolator());
        freeDownControl.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float t = (float) animation.getAnimatedValue();
                freeDownDistance = (float) (10 * Math.sqrt(maxFreeDownDistance/5)*t - 5 * t * t);

            }
        });
        freeDownControl.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                loadingStatus = LoadingStatus.FREE;
                isAnimationShowing = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isAnimationShowing = false;
                //重新开启动画
                Log.e("freeDown","end");
                startAllAnimator();
            }
        });
        animatorSet = new AnimatorSet();
        animatorSet.play(downControl).before(upControl);
    }

    public void startAllAnimator(){
        if(isAnimationShowing){
            return;
        }
        if(animatorSet.isRunning()){
            animatorSet.end();
            animatorSet.cancel();
        }
        if(freeDownControl.isRunning()){
            freeDownControl.cancel();
        }
        loadingStatus = LoadingStatus.DOWN;
        animatorSet.start();
    }

    private void initAttr(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LoadingView);
        ballColor = typedArray.getColor(R.styleable.LoadingView_ball_color, Color.BLUE);
        lineColor = typedArray.getColor(R.styleable.LoadingView_line_color,Color.BLUE);
        lineWidth = typedArray.getDimensionPixelOffset(R.styleable.LoadingView_line_width,200);
        strokeWidth = typedArray.getDimensionPixelSize(R.styleable.LoadingView_stroke_width,4);
        maxDownDistance = typedArray.getDimensionPixelSize(R.styleable.LoadingView_max_down,50);
        maxFreeDownDistance = typedArray.getDimensionPixelSize(R.styleable.LoadingView_max_up,50);
        maxUpDistance = typedArray.getDimensionPixelSize(R.styleable.LoadingView_max_up,50);
        ballRadius = typedArray.getDimensionPixelSize(R.styleable.LoadingView_ball_radius,10);
        typedArray.recycle(); //回收
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //创建时回调
        isRunning = true;
        new Thread(this).start();
    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        //
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        //被销毁时回调 资源回收
        isRunning = false;
        if(animatorSet.isRunning()){
            animatorSet.end();
            animatorSet.cancel();
        }
        if(freeDownControl.isRunning()){
            freeDownControl.end();
            freeDownControl.cancel();
        }

    }

    @Override
    public void run() {
        //绘制动画 (死循环）
        while(isRunning){
            drawView(); //绘制开始view
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void drawView() {
        //创建画布
        try {

        if(holder != null){
            canvas = holder.lockCanvas();
            //清空屏幕，消除残影，但会把背景变黑
            canvas.drawColor(0, PorterDuff.Mode.CLEAR);
            canvas.drawColor(0xFFFFFFFF); //覆盖屏幕为白
            paint.setColor(lineColor);
            path.reset();
            path.moveTo(getWidth()/2f-lineWidth/2f,getHeight()/2f);
            if(loadingStatus == LoadingStatus.DOWN){
                //小球当前在绳子上下降
                /*
                    二阶贝塞尔公式
                    B（t） = (1 - t)^2 * P + 2t(1 - t) * P1 + t^2 * p2
                    (P为起点   P1为控制点   P2为中点  B（t）为t情况下延长线头的坐标)
                    本例中 t = 0.5  求P1控制点y坐标（x坐标已知）
                    cp[1].x = (cp[0].x+cp[2].x)/2; 即连线中点
                    float c0 = (1 - t) * (1 - t); 0.25
                    float c1 = 2 * t * (1 - t);
                    float c2 = t * t;
                    grownX = c0 * cp[0].x + c1 * cp[1].x + c2 * cp[2].x;
                    grownY = c0 * cp[0].y + c1 * cp[1].y + c2 * cp[2].y;
                    cp[1].y = (grownY - 0.5cp[0].y)*2
                 */
                path.rQuadTo(lineWidth/2f,2*downDistance,lineWidth,0);
                paint.setColor(lineColor);
                paint.setStyle(Paint.Style.STROKE);
                canvas.drawPath(path,paint);
                //绘制小球
                paint.setColor(ballColor);
                paint.setStyle(Paint.Style.FILL);
                canvas.drawCircle(getWidth()/2.0f,getHeight()/2.0f+downDistance-ballRadius-strokeWidth/2f,ballRadius,paint);
            }else {
                //小球绳子上上升（自由落体）
                path.rQuadTo(lineWidth/2f,2*(maxDownDistance-upDistance),lineWidth,0);
                paint.setColor(lineColor);
                paint.setStyle(Paint.Style.STROKE);
                canvas.drawPath(path,paint);
                //绘制小球
                paint.setColor(ballColor);
                paint.setStyle(Paint.Style.FILL);
                if(loadingStatus == LoadingStatus.FREE){
                    canvas.drawCircle(getWidth()/2.0f,getHeight()/2.0f-freeDownDistance-ballRadius-strokeWidth/2f,ballRadius,paint);
                }else {
                    canvas.drawCircle(getWidth()/2.0f,getHeight()/2.0f+(maxDownDistance-upDistance)-ballRadius-strokeWidth/2f,ballRadius,paint);
                }
            }
            paint.setColor(ballColor);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(getWidth()/2.0f-lineWidth/2.0f,getHeight()/2.0f,ballRadius,paint);
            canvas.drawCircle(getWidth()/2.0f+lineWidth/2.0f,getHeight()/2.0f,ballRadius,paint);
        }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(canvas != null){
                assert holder != null;
                holder.unlockCanvasAndPost(canvas);
            }
        }
    }

    class ShockInterpolator implements Interpolator{

        @Override
        public float getInterpolation(float input) {
            float value = (float) (1-Math.exp(-3*input)*Math.cos(10*input));
            return value;
        }
    }

    private int calculateColor(int colorForm,int colorTo) {
        float fraction = 0;
        if (loadingStatus == LoadingStatus.DOWN) {
            fraction = 0.5f + downDistance / maxDownDistance / 2f;
        }else if(loadingStatus == LoadingStatus.UP){
            fraction = 1 - upDistance / maxUpDistance / 2f;
        }else {
            fraction = 0.5f - freeDownDistance / maxFreeDownDistance / 2;
        }
        int R = (int)(Color.red(colorForm)+(Color.red(colorTo)-Color.red(colorForm)*fraction));
        int G = (int)(Color.green(colorForm)+(Color.green(colorTo)-Color.green(colorForm)*fraction));
        int B = (int)(Color.blue(colorForm)+(Color.blue(colorTo)-Color.blue(colorForm)*fraction));
        return Color.rgb(R,G,B);
    }
}
