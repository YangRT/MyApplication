package com.example.administrator.myapplication.view.irregular_wave;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.example.administrator.myapplication.R;

public class IrregularWaveView extends View {

    private Paint mPaint;
    private int mItemWaveLength = 0;
    private int dx = 0;

    private Bitmap BmpSRC;
    private Bitmap BmpDST;


    public IrregularWaveView(Context context) {
        this(context,null);
    }

    public IrregularWaveView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public IrregularWaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mPaint = new Paint();
        BmpDST = BitmapFactory.decodeResource(getResources(), R.drawable.wav, null);
        BmpSRC = BitmapFactory.decodeResource(getResources(), R.drawable.circle_shape, null);
        //不要让它超出边界
        mItemWaveLength = BmpDST.getWidth() - BmpSRC.getWidth();

        startAnim();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //先画上圆形
        canvas.drawBitmap(BmpSRC, 0f, 0f, mPaint);

        //再画上结果
        int layerId = canvas.saveLayer(0f, 0f, (float) getWidth(), (float) getHeight(), null, Canvas.ALL_SAVE_FLAG);
        canvas.drawBitmap(
                BmpDST,
                new Rect(dx, 0, dx + BmpSRC.getWidth(), BmpSRC.getHeight()),
                new Rect(0, 0, BmpSRC.getWidth(), BmpSRC.getHeight()),
                mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawBitmap(BmpSRC, 0f, 0f, mPaint);
        mPaint.setXfermode(null);
        canvas.restoreToCount(layerId);
    }

    private void startAnim(){
        ValueAnimator animator = ValueAnimator.ofInt(0, mItemWaveLength);
        animator.setDuration(2000);
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
