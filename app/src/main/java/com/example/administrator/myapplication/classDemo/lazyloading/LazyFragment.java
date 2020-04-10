package com.example.administrator.myapplication.classDemo.lazyloading;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
//实现懒加载
public abstract class LazyFragment extends Fragment {

    boolean isFragmentVisible = false;
    boolean viewIsCreated = false;
    View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(rootView == null){
            rootView = inflater.inflate(getResId(),container,false);
        }
        initView();
        viewIsCreated = true;
        //setUserVisibleHint调用时期比fragment早，所以viewIsCreated为false
        //当onCreatedView执行后，如果getUserVisibleHint()为true即Fragment可见
        //需再次调用来分发状态
        if(getUserVisibleHint()){
           setUserVisibleHint(true);
        }
        return rootView;
    }

    protected abstract void dispatchUserVisible(boolean visible);

    protected abstract void initView();

    protected abstract int getResId();

    public  void onFragmentLoading(){}

    public void onFragmentLoadStop(){}

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(viewIsCreated){
            if(isVisibleToUser && !isFragmentVisible){
                dispatchUserVisible(true);
            }else if(!isVisibleToUser && isFragmentVisible){
                dispatchUserVisible(false);
            }
        }
    }
}
