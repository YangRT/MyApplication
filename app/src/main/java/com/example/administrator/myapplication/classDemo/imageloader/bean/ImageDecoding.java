package com.example.administrator.myapplication.classDemo.imageloader.bean;

import android.graphics.BitmapFactory;

import com.example.administrator.myapplication.classDemo.imageloader.configuration.ImageLoaderConfig;
import com.example.administrator.myapplication.classDemo.imageloader.downloader.ImageDownloader;
//图片解码对象
public class ImageDecoding {

    //图片路径
    private String loadUrl;
    //图片缓存key
    private String cacheKey;
    //图片显示的编码方式
    //private ImageSize targetSize;
    //ImageView 的ScaleType 属性
    // private ViewScaleType viewScaleType;
    //图片下载器
    private ImageDownloader imageDownloader;

    public String getLoadUrl() {
        return loadUrl;
    }

    public void setLoadUrl(String loadUrl) {
        this.loadUrl = loadUrl;
    }

    public String getCacheKey() {
        return cacheKey;
    }

    public void setCacheKey(String cacheKey) {
        this.cacheKey = cacheKey;
    }

    public ImageDownloader getImageDownloader() {
        return imageDownloader;
    }

    public void setImageDownloader(ImageDownloader imageDownloader) {
        this.imageDownloader = imageDownloader;
    }

    public BitmapFactory.Options getDecodingOptions() {
        return decodingOptions;
    }

    public void setDecodingOptions(BitmapFactory.Options decodingOptions) {
        this.decodingOptions = decodingOptions;
    }

    //图片解码设置
    private BitmapFactory.Options decodingOptions;

//    public ImageLoaderConfig(String cacheKey,String loadUrl,ImageSize targetSize,
//                             ViewScaleType viewScaleType,ImageDownloader imageDownloader,ImageLoaderConfig config){
//        this.cacheKey = cacheKey;
//        this.loadUrl = loadUrl;
//        this.targetSize = targetSize;
//        this.viewScaleType = viewScaleType;
//        this.imageDownloader = imageDownloader;
//
//    }

}
