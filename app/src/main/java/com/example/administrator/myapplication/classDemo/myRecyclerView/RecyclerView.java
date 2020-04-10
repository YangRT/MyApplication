package com.example.administrator.myapplication.classDemo.myRecyclerView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.classDemo.myRecyclerView.Adapter;
import com.example.administrator.myapplication.classDemo.myRecyclerView.Recycler;

import java.util.ArrayList;
import java.util.List;

//简单实现垂直方向recyclerView,并实现view的回收复用
public class RecyclerView extends ViewGroup {
    int[] heights;  //各item高度
    int rowCount;  // item总数
    int scrollY;   // y的偏移量 第一个item的上边界距离屏幕距离
    List<View> viewList; // 缓存已加载view
    int currentY;  // 记录Y轴滑动距离
    int firstRow;  // 屏幕第一个item的position
    Recycler recycler;
    int touchSlop;
    boolean needRelayout=true; //是否为加载第一屏
    int width;
    int height;
    Adapter adapter;

    //设置适配器
    public void  setAdapter(Adapter adapter){
        this.adapter = adapter;
        if(adapter != null){
            recycler = new Recycler(adapter.getViewTypeCount());
        }
        scrollY = 0;
        firstRow = 0;
        requestLayout();
    }

    public RecyclerView(Context context) {
        super(context);
    }

    public RecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public RecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs) {
        viewList = new ArrayList<>();
        final ViewConfiguration configuration = ViewConfiguration.get(context);
        this.touchSlop = configuration.getScaledTouchSlop();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if(needRelayout || changed){
            needRelayout = false;
            viewList.clear();
            removeAllViews();
            if (adapter != null){
                width = r - l;
                height = b - t;
                int left,top = 0,right,bottom;
                for(int i = 0;i < rowCount && top < height;i++){
                    bottom = top + heights[i];
                    View view = makeView(i,0,top,width,bottom);
                    viewList.add(view);
                    top = bottom;
                }
            }

        }
    }

    private View makeView(int position, int left, int top, int right, int bottom) {
        //生成view
        View view = obtainView(position,right-left,bottom-top);
        //摆放view
        view.layout(left,top,right,bottom);
        return view;
    }

    private View obtainView(int position, int w, int h) {
        int viewType = adapter.getViewType(position);
        //回收池
        View recyclerView = recycler.get(viewType);
        View view;
        if(recyclerView == null){
           view = adapter.onCreateViewHolder(position,recyclerView,this);
           if(view == null){
               throw new RuntimeException("onCreateViewHolder返回异常");
           }
        }else {
            view = adapter.onBinderViewHolder(position, recyclerView, this);
        }
            view.setTag(R.id.tag_type_view,viewType);
            view.measure(MeasureSpec.makeMeasureSpec(w,MeasureSpec.EXACTLY),MeasureSpec.makeMeasureSpec(h,MeasureSpec.EXACTLY));
            addView(view,0);
            return view;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        final int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int h = 0;
        if(adapter != null){
            this.rowCount = adapter.getCount();
            heights = new int[rowCount];
            for(int i=0;i<heights.length;i++){
                heights[i] = adapter.getHeight(i);
            }
        }

        int temH = sumArray(heights,0,heights.length);
        h = Math.min(heightSize,temH);
        setMeasuredDimension(widthSize,h);
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
    }

    private int sumArray(int[] array,int first,int last){
        int sum=0;
        int count = last + first;
        for(int i= first;i<count;i++){
            sum += array[i];
        }
        return sum;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = false;
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                currentY = (int)ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int Y2 = Math.abs(currentY - (int)ev.getRawY());
                if(Y2 > touchSlop){
                    intercept = true;
                }
                break;
        }
        return intercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:
                int Y2 = (int)event.getRawY();
                // 上滑为正 下滑为负
                int diff = currentY - Y2;
                scrollBy(0,diff); //当前画布
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void scrollBy(int x, int y) {
        scrollY += y;
        scrollY = scrollBounds(scrollY,firstRow,heights,height);
        // + 上滑  - 下滑
        if(scrollY > 0){
            while(heights[firstRow] < scrollY){
                if(!viewList.isEmpty()){
                    removeView(viewList.remove(0));
                }
                scrollY -= heights[firstRow];
                firstRow++;
            }
            while(getFilledHeight() < height){
                int addLast = firstRow + viewList.size();
                View view = obtainView(addLast,width,heights[addLast]);
                viewList.add(viewList.size(),view);
            }
        }else if(scrollY < 0){
            while(!viewList.isEmpty() && getFilledHeight() - heights[firstRow + viewList.size()-1] > height){
                removeView(viewList.remove(viewList.size()-1));

            }
            while (0 > scrollY){
                int firstAddRow = firstRow - 1;
                View view = obtainView(firstAddRow,width,heights[firstAddRow]);
                viewList.add(0,view);
                firstRow--;
                scrollY += heights[firstRow+1];
            }
        }
        // 重新摆放
        repositionViews();
        awakenScrollBars();
    }

    private int scrollBounds(int scrollY,int firstRow,int sizes[],int viewSize){
        if(scrollY>0){
            scrollY = Math.min(scrollY,sumArray(sizes,firstRow,sizes.length-firstRow)-viewSize);
        }else {
            scrollY = Math.max(scrollY,-sumArray(sizes,0,firstRow));
        }
        return scrollY;
    }

    @Override
    public void removeView(View view) {
        super.removeView(view);
        int type = (int)view.getTag(R.id.tag_type_view);
        recycler.put(type,view);
    }

    private void repositionViews() {
        int top,bottom,i;
        top = -scrollY;
        i = firstRow;
        for(View view: viewList){
            bottom = top + heights[i++];
            view.layout(0,top,width,bottom);
            top = bottom;
        }
    }

    private int getFilledHeight() {
        return sumArray(heights,firstRow,viewList.size()) - scrollY;
    }
}
