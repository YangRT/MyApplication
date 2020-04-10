package com.example.administrator.myapplication.classDemo.imageloader.configuration;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;

import com.example.administrator.myapplication.classDemo.imageloader.cache.DiskCache;
import com.example.administrator.myapplication.classDemo.imageloader.decode.BaseImageDecoder;
import com.example.administrator.myapplication.classDemo.imageloader.decode.ImageDecoder;
import com.example.administrator.myapplication.classDemo.imageloader.display.BitmapDisplayer;
import com.example.administrator.myapplication.classDemo.imageloader.display.SimpleBitmapDispalyer;
import com.example.administrator.myapplication.classDemo.imageloader.downloader.BaseImageDownloader;
import com.example.administrator.myapplication.classDemo.imageloader.downloader.ImageDownloader;
import com.example.administrator.myapplication.classDemo.imageloader.filename.FileNameGenerator;
import com.example.administrator.myapplication.classDemo.imageloader.utils.Constants;
import com.example.administrator.myapplication.classDemo.imageloader.view.ImageScaleType;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;


public class ImageLoaderConfig {

    private Resources resources;

    //本地缓存图片最大宽度，不设置则为原图宽度
    private int maxImageWidthForDiskcache;

    //本地缓存图片最大高度，不设置则为原图高度
    private int maxImageHeightDiskcache;

    private DiskCache diskCache;

    //图片下载器
    private ImageDownloader imageLoader;

    //图片解码器
    private ImageDecoder imageDecoder;

    //下载图片是否缓存在SD卡
    private boolean cacheOnDisk;

    //图片显示的编码方式(完全按比例缩小）
    private ImageScaleType imageScaleType;

    //图片解码设置
    private BitmapFactory.Options decodingOptions;


    //图片显示器
    private BitmapDisplayer bitmapDisplayer;


    private ImageLoaderConfig(Builder builder){
        resources = builder.context.getResources();
        bitmapDisplayer = builder.bitmapDisplayer;
        imageDecoder = builder.imageDecoder;
        imageLoader = builder.imageLoader;
        //缓存在SD卡中
        cacheOnDisk = true;

        maxImageHeightDiskcache = 800;
        maxImageWidthForDiskcache = 400;


        decodingOptions = new BitmapFactory.Options();
        decodingOptions.inPreferredConfig = Bitmap.Config.RGB_565;
    }

    public static void init(Context context,String disk){
        ImageLoaderConfig.simpleConfig(context,disk);
    }


    private static ImageLoaderConfig simpleConfig(Context context,String disk){
        File cache; //缓存文件的目录
        if(TextUtils.isEmpty(disk)){
            disk = Constants.IMAGE_DIR_ROOT + context.getPackageName()+ Constants.IMAGE_CACHE_DIR;
        }
        cache = new File(disk);
        return new Builder(context)
             //   .cache(new )
                .displayer(new SimpleBitmapDispalyer())
                .decoder(new BaseImageDecoder())
                .downloader(new BaseImageDownloader(context))
                .build();
        
    }

//    private static DiskCache createDiskcache(File cacheDir, FileNameGenerator generator){
//        try {
//            return null;
//        }catch (IOException e){
//            Log.e(Constants.LOG_TAG,"创建本地缓存出现异常："+e.toString());
//            e.printStackTrace();
//        }
//        return null;
//    }

    public static class Builder{

        private Context context;

        private DiskCache diskCache;

        //图片下载器
        private ImageDownloader imageLoader;

        //图片解码器
        private ImageDecoder imageDecoder;

        //图片显示器
        private BitmapDisplayer bitmapDisplayer;

        Builder(Context context){
            this.context = context;
        }


        Builder cache(DiskCache cache){
            this.diskCache = cache;
            return this;
        }
        Builder downloader(ImageDownloader imageLoader){
            this.imageLoader = imageLoader;
            return this;
        }

        Builder decoder(ImageDecoder imageDecoder){
            this.imageDecoder = imageDecoder;
            return this;
        }

        Builder displayer(BitmapDisplayer bitmapDisplayer){
            this.bitmapDisplayer = bitmapDisplayer;
            return this;
        }

        ImageLoaderConfig build(){
            return new ImageLoaderConfig(this);
        }


    }
}
