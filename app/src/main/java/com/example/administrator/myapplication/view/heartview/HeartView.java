package com.example.administrator.myapplication.view.heartview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.example.administrator.myapplication.R;

public class HeartView extends View {

    private Paint mPaint;
    private int mItemWaveLength = 0;
    private int dx = 0;

    private Bitmap BmpSRC;
    private Bitmap BmpDST;


    public HeartView(Context context) {
        this(context,null);
    }

    public HeartView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public HeartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){

        mPaint = new Paint();
        mPaint.setColor(Color.RED);

        BmpDST = BitmapFactory.decodeResource(getResources(), R.drawable.heartmap, null);
        BmpSRC = Bitmap.createBitmap(BmpDST.getWidth(), BmpDST.getHeight(), Bitmap.Config.ARGB_8888);

        mItemWaveLength = BmpDST.getWidth();

        startAnim();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Canvas c = new Canvas(BmpSRC);
        //清空bitmap
        c.drawColor(Color.RED, PorterDuff.Mode.CLEAR);
        Log.d("onDraw","左移动:${BmpDST.width - dx}");

        //画上矩形
        c.drawRect((float)(BmpDST.getWidth() - dx), 0f, (float)BmpDST.getWidth(),(float) BmpDST.getHeight(), mPaint);

        //模式合成
        int layerId = canvas.saveLayer(0f, 0f, (float)getWidth(), (float)getHeight(), null, Canvas.ALL_SAVE_FLAG);
        canvas.drawBitmap(BmpDST, 0f, 0f, mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawBitmap(BmpSRC, 0f, 0f, mPaint);
        mPaint.setXfermode(null);
        canvas.restoreToCount(layerId);
    }

    private void startAnim(){
        ValueAnimator animator = ValueAnimator.ofInt(0, mItemWaveLength);
        animator.setDuration(6000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                dx = (int)animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.start();

    }
}
