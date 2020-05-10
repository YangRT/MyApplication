package com.example.administrator.myapplication.view.filter_view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.administrator.myapplication.R;

public class FilterView extends View {

    private Paint paint;

    private Bitmap bitmap;

    float showHeight = 0;

    public FilterView(Context context) {
        this(context,null);
    }

    public FilterView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public FilterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.girl);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //1. 缩放运算---乘法 -- 颜色增强
        ColorMatrix colorMatrix = new ColorMatrix(new float[]{
                1.2f, 0f, 0f, 0f, 0f,
                0f, 1.2f, 0f, 0f, 0f,
                0f, 0f, 1.2f, 0f, 0f,
                0f, 0f, 0f, 1.2f, 0f
        });

        RectF rectF = new RectF(
                0f,
                showHeight*1f ,
                (bitmap.getWidth() / 2f),
                (bitmap.getHeight() / 4f)
        );
        drawFilterBitmap(colorMatrix, canvas,rectF);

        showHeight += bitmap.getHeight() / 4f;

        //2 平移运算---加法
        ColorMatrix colorMatrix2 = new ColorMatrix(new float[]{
                1f, 0f,0f,0f,0f,
                0f,1f,0f,0f,100f,
                0f,0f,1f,0f,0f,
                0f,0f,0f,1f,0f}
        );

        RectF rectF2 = new RectF(
                0f,
                showHeight,
                bitmap.getWidth() / 2f,
                (bitmap.getHeight() /4f) * 2
        );
        drawFilterBitmap(colorMatrix2, canvas,rectF2);


        showHeight += bitmap.getHeight() / 4f;



        //3. 反相效果 -- 底片效果
        ColorMatrix colorMatrix3 =  new ColorMatrix(new float[]{
                -1f, 0f,0f,0f,255f,
                0f,-1f,0f,0f,255f,
                0f,0f,-1f,0f,255f,
                0f,0f,0f,1f,0f}
        );

        RectF rectF3 = new RectF(
                0f,
                showHeight,
                bitmap.getWidth() / 2f,
                (bitmap.getHeight() /4) * 3f
        );
        drawFilterBitmap(colorMatrix3, canvas,rectF3);


        /**
         * 4.黑白照片
         * 是将我们的三通道变为单通道的灰度模式
         * 去色原理：只要把R G B 三通道的色彩信息设置成一样，那么图像就会变成灰色，
         * 同时为了保证图像亮度不变，同一个通道里的R+G+B =1
         */
        ColorMatrix colorMatrix4 =  new ColorMatrix(new float[]{
                0.213f, 0.715f,0.072f,0f,0f,
                0.213f, 0.715f,0.072f,0f,0f,
                0.213f, 0.715f,0.072f,0f,0f,
                0f,0f,0f,1f,0f});


        RectF rectF4 = new RectF(
                bitmap.getWidth()/2f,
                showHeight,
                bitmap.getWidth(),
                (bitmap.getHeight() /4) * 3f
        );
        drawFilterBitmap(colorMatrix4, canvas,rectF4);

        showHeight -= bitmap.getHeight() / 4f;

        //5.发色效果---（比如红色和绿色交换）
        ColorMatrix colorMatrix5 =  new ColorMatrix(new float[]{
                1f,0f,0f,0f,0f,
                0f, 0f,1f,0f,0f,
                0f,1f,0f,0f,0f,
                0f,0f,0f,0.5F,0f}
        );

        RectF rectF5 = new RectF(
                bitmap.getWidth() / 2f,
                showHeight,
                bitmap.getWidth(),
                (bitmap.getHeight() /4)*2
        );
        drawFilterBitmap(colorMatrix5, canvas,rectF5);

        showHeight -= bitmap.getHeight() / 4f;

//        //6.复古效果
        ColorMatrix colorMatrix6=  new ColorMatrix(new float[]{
                1/2f,1/2f,1/2f,0f,0f,
                1/3f, 1/3f,1/3f,0f,0f,
                1/4f,1/4f,1/4f,0f,0f,
                0f,0f,0f,1f,0f});

        RectF rectF6 = new RectF(
                bitmap.getWidth() / 2f,
                showHeight,
                bitmap.getWidth()*1f,
                (float) (bitmap.getHeight() /4)
        );
        drawFilterBitmap(colorMatrix6, canvas,rectF6);

    }

    private void drawFilterBitmap(ColorMatrix colorMatrix,Canvas canvas,RectF rectF) {

        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        canvas.drawBitmap(bitmap, null, rectF, paint);
    }

}
