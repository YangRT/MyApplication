package com.example.administrator.myapplication.view.scaleimageview;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewTreeObserver;


public class ScaleImageView extends android.support.v7.widget.AppCompatImageView implements ViewTreeObserver.OnGlobalLayoutListener, View.OnTouchListener, ScaleGestureDetector.OnScaleGestureListener {

    //是否初次加载
    private boolean isFirst = false;
    //初始化图片缩放值
    private float baseScale;
    //图片最大放大值
    private float maxScale;
    //图片矩阵
    private Matrix imageMatrix;
    //检测两个手指缩放手势
    private ScaleGestureDetector scaleGestureDetector;




    public ScaleImageView(Context context) {
        super(context);
        init(context);
    }

    public ScaleImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ScaleImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        imageMatrix = new Matrix();
        super.setScaleType(ScaleType.MATRIX);
        scaleGestureDetector = new ScaleGestureDetector(context,this);
        setOnTouchListener(this);
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        // 前一个伸缩事件至当前伸缩事件的伸缩比率
        float scaleFactor = detector.getScaleFactor();
        float scale = getScale();
        if (null == getDrawable()){
            return true;
        }
        // 控件图片的缩放范围
        if ((scale < maxScale && scaleFactor > 1.0f)
                || (scale > baseScale && scaleFactor < 1.0f)) {
            if (scale * scaleFactor < baseScale) {
                scaleFactor = baseScale / scale;
            }
            if (scale * scaleFactor > maxScale) {
                scaleFactor = maxScale / scale;
            }
            // 以屏幕中央位置进行缩放
            // mScaleMatrix.postScale(scaleFactor, scaleFactor, getWidth() / 2,
            // getHeight() / 2);
            // 以手指所在地方进行缩放
            imageMatrix.postScale(scaleFactor, scaleFactor,
                    detector.getFocusX(), detector.getFocusY());
            borderAndCenterCheck();
            setImageMatrix(imageMatrix);
        }
        return false;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        scaleGestureDetector.onTouchEvent(event);
        return true;//这里要return true才行
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        //注册OnGlobalLayoutListener
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        //移除OnGlobalLayoutListener
        getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }

    // 当View加载完成时可能通过OnGlobalLayoutListener监听，在布局加载完成后获得一个view的宽高。
    @Override
    public void onGlobalLayout() {
        if(!isFirst){
            isFirst = true;
            //控件宽高
            int width = getWidth();
            int height = getHeight();
            Drawable d = getDrawable();
            if(d == null){
                return;
            }
            //获取图片宽高
            int dw = d.getIntrinsicWidth();
            int dh = d.getIntrinsicHeight();
            float scale = 1.0f;
            //图片宽度大于控件宽度且高度大于控件高度
            if(dw > width && dh > height){
                //缩小一定值
                scale = width * 1.0f / dw;
            }
            // 图片宽度大于控件宽度但高度小于控件高度& 图片的宽高都小于控件的宽高
            if ((dw < width && dh < height) || (dw > width && dh < height)) {
                // 按照宽度对应缩放最小值进行缩放
                scale = Math.min(width * 1.0f / dw, height * 1.0f / dh);
            }
            // 图片宽度小于控件宽度，但图片高度大于控件高度
            if (dw < width && dh > height) {
                // 缩小一定的比例
                scale = height * 1.0f / dh;
            }
            baseScale = scale;
            maxScale = baseScale * 4;
            //将图片移到手机屏幕中间
            float dx = width/2 - dw/2;
            float dy = height/2 - dh/2;
            imageMatrix.postTranslate(dx, dy);
            imageMatrix.postScale(baseScale, baseScale, width / 2,
                    height / 2);
            setImageMatrix(imageMatrix);
        }
    }

    private float getScale() {
        float[] values = new float[9];
        imageMatrix.getValues(values);
        return values[Matrix.MSCALE_X];
    }

    /**
     * 图片在缩放时进行边界控制
     */
    private void borderAndCenterCheck() {
        RectF rect = getMatrixRectF();
        float deltaX = 0;
        float deltaY = 0;
        int width = getWidth();
        int height = getHeight();
        // 缩放时进行边界检测，防止出现白边
        if (rect.width() >= width) {
            if (rect.left > 0) {
                deltaX = -rect.left;
            }
            if (rect.right < width) {
                deltaX = width - rect.right;
            }
        }
        if (rect.height() >= height) {
            if (rect.top > 0) {
                deltaY = -rect.top;
            }
            if (rect.bottom < height) {
                deltaY = height - rect.bottom;
            }
        }
        // 如果宽度或者高度小于控件的宽或者高；则让其居中
        if (rect.width() < width) {
            deltaX = width / 2f - rect.right + rect.width() / 2f;

        }
        if (rect.height() < height) {
            deltaY = height / 2f - rect.bottom + rect.height() / 2f;
        }
        imageMatrix.postTranslate(deltaX, deltaY);
    }

    /**
     * 获得图片放大缩小以后的宽和高
     *
     * @return
     */
    private RectF getMatrixRectF() {
        Matrix matrix = imageMatrix;
        RectF rectF = new RectF();
        Drawable d = getDrawable();
        if (d != null) {
            rectF.set(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
            matrix.mapRect(rectF);
        }
        return rectF;
    }

}
