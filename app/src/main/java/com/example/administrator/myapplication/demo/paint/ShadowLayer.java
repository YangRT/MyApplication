package com.example.administrator.myapplication.demo.paint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class ShadowLayer extends View {

    private Paint paint;

    public ShadowLayer(Context context) {
        this(context,null);
    }

    public ShadowLayer(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ShadowLayer(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(Color.BLACK);
        // 设置透明度
        paint.setAlpha(80);
        // 关闭硬件加速
        setLayerType(View.LAYER_TYPE_SOFTWARE,null);
        //
        paint.setShadowLayer(50f,10f,10f,Color.RED);
        paint.setTextSize(50f);

        int radius = 200;
        int offset = 40;

        float startX = getWidth() / 2f - radius;
        float startY = getHeight() / 2f;
        canvas.drawText("画一个圆",getWidth()/2f-100f,getHeight()/2f-300f,paint);
        canvas.drawCircle(startX,startY,radius,paint);

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5f);
        paint.setShadowLayer(50f, -20f, 10f, Color.RED);
        canvas.drawCircle(startX + radius * 2 + offset, startY, radius, paint);
    }
}
