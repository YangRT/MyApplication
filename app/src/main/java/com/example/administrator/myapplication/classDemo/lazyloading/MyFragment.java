package com.example.administrator.myapplication.classDemo.lazyloading;

import android.view.LayoutInflater;

import com.example.administrator.myapplication.R;

public class MyFragment extends LazyFragment {

    @Override
    public void onFragmentLoading() {
        super.onFragmentLoading();

    }

    @Override
    public void onFragmentLoadStop() {
        super.onFragmentLoadStop();
    }

    @Override
    protected void dispatchUserVisible(boolean visible) {
        isFragmentVisible = visible;
        if(visible){
            onFragmentLoading();
        }else {
            onFragmentLoadStop();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!isFragmentVisible && getUserVisibleHint()){
            dispatchUserVisible(true);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(isFragmentVisible && getUserVisibleHint()){
            dispatchUserVisible(false);
        }
    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getResId() {
        return R.layout.fragment_b;
    }
}
