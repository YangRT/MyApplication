package com.example.administrator.myapplication.classDemo.eventBus;

import java.lang.reflect.Method;

public class SubscribeMethod {
    //回调方法
    private Method mMethod;

    //线程模式
    private ThreadMode mThreadMode;

    //回调方法的参数类
    private Class<?> type;


    public SubscribeMethod(Method mMethod, ThreadMode mThreadMode, Class<?> type) {
        this.mMethod = mMethod;
        this.mThreadMode = mThreadMode;
        this.type = type;
    }

    public Method getmMethod() {
        return mMethod;
    }

    public void setmMethod(Method mMethod) {
        this.mMethod = mMethod;
    }

    public ThreadMode getmThreadMode() {
        return mThreadMode;
    }

    public void setmThreadMode(ThreadMode mThreadMode) {
        this.mThreadMode = mThreadMode;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

}
