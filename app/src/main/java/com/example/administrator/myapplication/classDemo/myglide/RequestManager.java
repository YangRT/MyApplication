package com.example.administrator.myapplication.classDemo.myglide;

import java.util.concurrent.LinkedBlockingQueue;

public class RequestManager {

    private static RequestManager manager = null;

    //创建线程数组
    private BitmapDispatcher[] dispatchers;

    //请求队列
    private LinkedBlockingQueue<BitmapRequest> requestQueue = new LinkedBlockingQueue<>();

    public static RequestManager getInstance(){
        if(manager == null){
            synchronized (RequestManager.class){
                if(manager == null){
                    manager = new RequestManager();
                }
            }
        }
        return manager;
    }

    private RequestManager(){
        start();
    }

    private void start(){
        stop();
        startAllDispatcher();
    }

    private void startAllDispatcher(){
        int threadCount = Runtime.getRuntime().availableProcessors();
        dispatchers = new BitmapDispatcher[threadCount];
        for(int i = 0;i < threadCount;i++){
            BitmapDispatcher dispatcher = new BitmapDispatcher(requestQueue);
            dispatcher.start();
            dispatchers[i] = dispatcher;
        }
    }

    private void stop(){
        if(dispatchers != null && dispatchers.length > 0){
            for(int i = 0;i < dispatchers.length;i++){
                if(!dispatchers[i].isInterrupted()){
                    dispatchers[i].interrupt();
                }
            }
        }
    }

    //将图片请求加入队列
    public void addRequest(BitmapRequest request){
        if(request == null){
            return;
        }
        if(!requestQueue.contains(request)){
            requestQueue.add(request);
        }
    }


}
