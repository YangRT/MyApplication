package com.example.administrator.myapplication.view.zoom_imageview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.example.administrator.myapplication.R;

// 图片拥有 随手势部分放大功能
public class ZoomImageView extends android.support.v7.widget.AppCompatImageView {

    // 原图
    private Bitmap mBitmap;

    // 放大的图
    private Bitmap mBitmapScale;

    // 制作的圆形图片 （局部放大） 盖在 Canvas 上
    private ShapeDrawable mShapeDrawable;

    private Matrix mMatrix;

    //放大倍数
    private int FACTOR = 2;
    //放大镜的半径
    private int RADIUS = 160;


    public ZoomImageView(Context context) {
        this(context,null);
    }

    public ZoomImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ZoomImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.girl);
        mBitmapScale = mBitmap;
        mBitmapScale = Bitmap.createScaledBitmap(
                mBitmapScale, mBitmapScale.getWidth() * FACTOR,
                mBitmapScale.getHeight() * FACTOR, true);
        BitmapShader bitmapShader = new BitmapShader(mBitmapScale, Shader.TileMode.CLAMP,Shader.TileMode.CLAMP);

        mShapeDrawable = new ShapeDrawable(new OvalShape());
        mShapeDrawable.getPaint().setShader(bitmapShader);
        // 切出矩形区域，用来画圆（内切圆）
        mShapeDrawable.setBounds(0, 0, RADIUS * 2, RADIUS * 2);

        mMatrix = new Matrix();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 1、画原图
        canvas.drawBitmap(mBitmap, 0f, 0f, null);

        // 2、画放大镜的图
        mShapeDrawable.draw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int)event.getX();
        int y = (int)event.getY() - RADIUS;
        Log.d("onTouchEvent", "x:" + x + "y:" + y);

        // 将放大的图片往相反的方向挪动
        mMatrix.setTranslate((float) (RADIUS - x * FACTOR), (float) (RADIUS - y * FACTOR));
        mShapeDrawable.getPaint().getShader().setLocalMatrix(mMatrix);
        // 切出手势区域点位置的圆
        mShapeDrawable.setBounds(x - RADIUS, y - RADIUS, x + RADIUS, y + RADIUS);
//        invalidate()
        postInvalidate();
        return true;
    }
}
