package com.example.administrator.myapplication.classDemo.myglide;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.LinkedBlockingQueue;

public class BitmapDispatcher extends Thread {

    private Handler handler = new Handler(Looper.getMainLooper());

    //创建阻塞队列
    private LinkedBlockingQueue<BitmapRequest> requestQueue;

    public BitmapDispatcher(LinkedBlockingQueue requestQueue){
        this.requestQueue = requestQueue;
    }
    @Override
    public void run() {
        super.run();
        while (!isInterrupted()) {
            //从队列中获取请求
            try {
                BitmapRequest request = requestQueue.take();
                //设置占位图片
                setLoadingImage(request);
                //请求图片
                Bitmap bitmap = findBitmap(request);
                //将图片显示在imageView上
                showBitmap(request, bitmap);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void showBitmap(final BitmapRequest request, final Bitmap bitmap) {
        if(bitmap != null && request.getImageView() != null
                && request.getImageView().getTag().equals(request.getUrlMd5())){
            final ImageView imageView = request.getImageView();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    imageView.setImageBitmap(bitmap);
                    if(request.getListener() != null){
                        request.getListener().onSuccess(bitmap);
                    }
                }
            });
        }
    }

    private Bitmap findBitmap(BitmapRequest request) {
        return downloadBitmap(request.getUrl());
    }

    private Bitmap downloadBitmap(String url) {
        OutputStream outputStream = null;
        InputStream inputStream = null;
        Bitmap bitmap = null;
        try {
            URL u = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) u.openConnection();
            inputStream = connection.getInputStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                if(inputStream != null){
                    inputStream.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
            try {
                if(outputStream != null){
                    outputStream.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return bitmap;
    }

    private void setLoadingImage(BitmapRequest request) {
        if(request.getSrcId() > 0 && request.getImageView() != null){
            final int resId = request.getSrcId();
            final ImageView imageView = request.getImageView();
            handler.post(new Runnable() {
                @Override
                public void run() {
                  imageView.setImageResource(resId);
                }
            });
        }
    }
}
