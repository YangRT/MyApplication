package com.example.administrator.myapplication.classDemo.imageloader.utils;

import android.os.Environment;

//常量工具类
public class Constants {

    public static final String LOG_TAG = "ImageLoader===>";

    //SD卡详细路径
    public static final String SDCARD_ROOT = Environment.getExternalStorageDirectory().getAbsolutePath();

    //图片缓存路径
    public static final String IMAGE_DIR_ROOT = SDCARD_ROOT + "/Android/data/";

    //保存图片缓存路径
    public static final String IMAGE_CACHE_DIR = "/images/images_cache";
}
