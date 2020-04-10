package com.example.administrator.myapplication.classDemo.httputil;

public class MyHttp {

    public static<T,E> void sendJsonRequest(String url,T requestData,Class<E> response,JsonDatatransforListener listener){
        IHttpRequest request = new JsonHttpRequest();
        CallbackListener mListener = new JsonCallbackListener<>(response,listener);
        HttpTask task = new HttpTask(url,requestData,request,mListener);
        ThreadPoolManager.getInstance().addTask(task);


    }
}
