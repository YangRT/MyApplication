package com.example.administrator.myapplication.view.linear_gradient;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;

public class LinearGradientTextView extends android.support.v7.widget.AppCompatTextView {

    // 定义线性渐变
    private LinearGradient mLinearGradient;

    // 定义一个矩阵
    private Matrix mGradientMatrix;

    private Paint mPaint;

    private boolean mAnimating = true;

    private int mViewWidth;
    private int mTranslate;

    private int delta = 15;


    public LinearGradientTextView(Context context) {
        this(context,null);
    }

    public LinearGradientTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LinearGradientTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        Log.e("TextView","onSizeChanged");
        super.onSizeChanged(w, h, oldw, oldh);
        if (mViewWidth == 0) {
            mViewWidth = getMeasuredWidth();
            if (mViewWidth > 0) {
                mPaint = getPaint();
                String text = getText().toString();
                // float textWidth = mPaint.measureText(text);
                int size;
                if(text.length()>0)
                {
                    size = mViewWidth*2/text.length();
                }else{
                    size = mViewWidth;
                }
                mLinearGradient = new LinearGradient(-size, 0, 0, 0,
                        new int[] { 0x33ffffff, 0xffffffff, 0x33ffffff },
                        new float[] { 0, 0.5f, 1 }, Shader.TileMode.CLAMP); //边缘融合
                mPaint.setShader(mLinearGradient);
                mGradientMatrix = new Matrix();
            }
        }
    }



    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e("TextView","draw");
        int length = Math.max(length(), 1);
        if (mAnimating && mGradientMatrix != null) {
            float mTextWidth = getPaint().measureText(getText().toString());
            mTranslate += delta;
            if (mTranslate > mTextWidth+1 || mTranslate<1) {
                delta  = -delta;
            }
            mGradientMatrix.setTranslate(mTranslate, 0);
            mLinearGradient.setLocalMatrix(mGradientMatrix);
            postInvalidateDelayed(30);
        }

    }
}
