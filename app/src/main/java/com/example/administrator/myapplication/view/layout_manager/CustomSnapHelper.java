package com.example.administrator.myapplication.view.layout_manager;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.View;

public class CustomSnapHelper extends SnapHelper {


    @Nullable
    @Override
    public int[] calculateDistanceToFinalSnap(@NonNull RecyclerView.LayoutManager layoutManager, @NonNull View view) {
        if (layoutManager instanceof ScaleLayoutManger) {
            int[] out = new int[2];
            if (layoutManager.canScrollHorizontally()) {
                out[0] = ((ScaleLayoutManger) layoutManager).calculateDistanceToPosition(
                        layoutManager.getPosition(view));
                out[1] = 0;
            } else {
                out[0] = 0;
                out[1] = ((ScaleLayoutManger) layoutManager).calculateDistanceToPosition(
                        layoutManager.getPosition(view));
            }
            return out;
        }
        return null;
    }

    @Nullable
    @Override
    public View findSnapView(RecyclerView.LayoutManager layoutManager) {
        if (layoutManager instanceof ScaleLayoutManger) {
            int pos = ((ScaleLayoutManger) layoutManager).getFixedScrollPosition();
            if (pos != RecyclerView.NO_POSITION) {
                return layoutManager.findViewByPosition(pos);
            }
        }
        return null;
    }

    @Override
    public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int i, int i1) {
        return RecyclerView.NO_POSITION;
    }
}
