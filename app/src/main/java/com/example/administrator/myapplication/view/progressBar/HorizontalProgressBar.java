package com.example.administrator.myapplication.view.progressBar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ProgressBar;

import com.example.administrator.myapplication.R;

public class HorizontalProgressBar extends ProgressBar {


    private final static int DEFAULT_TEXT_SIZE = 10;
    private final static int DEFAULT_TEXT_COLOR = 0xFFFC00D1;
    private final static int DEFAULT_TEXT_OFFSET = 10;
    private final static int DEFAULT_COLOR_UNREACH = 0xFFD3D6DA;
    private final static int DEFAULT_HEIGHT_UNREACH = 2;
    private final static int DEFAULT_COLOR_REACH = DEFAULT_TEXT_COLOR;
    private final static int DEFAULT_HEIGHT_REACH = 2;

    private int textSize = sp2px(DEFAULT_TEXT_SIZE);
    private int textColor = DEFAULT_TEXT_COLOR;
    private int textOffset = dp2px(DEFAULT_TEXT_OFFSET);
    private int reachColor = DEFAULT_COLOR_REACH;
    private int reachHeight = dp2px(DEFAULT_HEIGHT_REACH);
    private int unreachColor = DEFAULT_COLOR_UNREACH;
    private int unreachHeight = dp2px(DEFAULT_HEIGHT_UNREACH);

    private Paint paint;

    private int realWidth;


    public HorizontalProgressBar(Context context) {
        this(context,null);
    }

    public HorizontalProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public HorizontalProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


    private void init(Context context, @Nullable AttributeSet attrs){
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.HorizontalProgressBar);
        textSize = (int)array.getDimension(R.styleable.HorizontalProgressBar_process_text_size,textSize);
        textColor = array.getColor(R.styleable.HorizontalProgressBar_process_text_color,textColor);
        textOffset = (int)array.getDimension(R.styleable.HorizontalProgressBar_process_text_offset,textOffset);

        unreachHeight = (int)array.getDimension(R.styleable.HorizontalProgressBar_process_unreach_height,unreachHeight);
        unreachColor = array.getColor(R.styleable.HorizontalProgressBar_process_unreach_color,unreachColor);
        reachHeight = (int)array.getDimension(R.styleable.HorizontalProgressBar_process_reach_height,reachHeight);
        reachColor = array.getColor(R.styleable.HorizontalProgressBar_process_reach_color,reachColor);
        array.recycle();

        paint = new Paint();
        paint.setTextSize(textSize);
    }


    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec);
        setMeasuredDimension(width,height);

        realWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
    }

    private int measureHeight(int heightMeasureSpec){
        int result=0;
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);
        if (mode == MeasureSpec.EXACTLY){
            result = size;
        }else {
            int textHeight = (int)(paint.descent() - paint.ascent());
            result = getPaddingTop() + getPaddingBottom() +
                    Math.max(Math.max(reachHeight,unreachHeight),Math.abs(textHeight));
            if (mode == MeasureSpec.AT_MOST){
                result = Math.min(result,size);
            }
        }
        return result;
    }


    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.translate(getPaddingLeft(),getHeight()/2);
        String text = getProgress() + "%";
        float radio = getProgress()*1.0f/getMax();
        int textWidth = (int)paint.measureText(text);
        float progressX =  radio*realWidth;
        boolean notNeedUnReach = false;
        if (progressX+textWidth > realWidth){
            progressX = realWidth - textWidth;
            notNeedUnReach = true;
        }
        float endX =progressX-textOffset/2;
        if (endX > 0){
            paint.setColor(reachColor);
            paint.setStrokeWidth(reachHeight);
            canvas.drawLine(0,0,endX,0,paint);
        }

        // draw text
        paint.setColor(textColor);
        int y = (int)(-(paint.descent()+paint.ascent()/2));
        canvas.drawText(text,progressX,y,paint);

        if (!notNeedUnReach){
            float start = progressX + textOffset/2 + textWidth;
            paint.setColor(unreachColor);
            paint.setStrokeWidth(unreachHeight);
            canvas.drawLine(start,0,realWidth,0,paint);
        }

        canvas.restore();

    }

    private int dp2px(int value){
        int result = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,value,getResources().getDisplayMetrics());
        return result;
    }

    private int sp2px(int value){
        int result = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,value,getResources().getDisplayMetrics());
        return result;
    }
}
