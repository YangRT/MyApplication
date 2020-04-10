package com.example.administrator.myapplication.classDemo.myglide;

import android.content.Context;
import android.widget.ImageView;

import java.lang.ref.SoftReference;

public class BitmapRequest {

    //图片地址
    private String url;

    //图片控件
    private SoftReference<ImageView> imageView;

    //回调对象
    private RequestListener listener;

    //图片占位图
    private int srcId;

    //图片唯一标识
    private String urlMd5;

    private Context context;

    public String getUrl() {
        return url;
    }

    public ImageView getImageView() {
        return imageView.get();
    }

    public RequestListener getListener() {
        return listener;
    }

    public int getSrcId() {
        return srcId;
    }

    public String getUrlMd5() {
        return urlMd5;
    }



    public BitmapRequest(Context context){
        this.context = context;
    }

    public BitmapRequest load(String url){
        this.url = url;
        this.urlMd5 = Md5Util.toMD5(url);
        return this;
    }

    public BitmapRequest loading(int srcId){
        this.srcId = srcId;
        return this;
    }

    public BitmapRequest listener(RequestListener listener){
        this.listener = listener;
        return this;
    }

    public void into(ImageView view){
        view.setTag(this.urlMd5);
        this.imageView = new SoftReference<>(view);
        //将图片请求添加队列中
        RequestManager.getInstance().addRequest(this);
    }
}
