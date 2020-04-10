package com.example.administrator.myapplication.classDemo.imageloader.decode;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.administrator.myapplication.classDemo.imageloader.bean.ImageDecoding;
import com.example.administrator.myapplication.classDemo.imageloader.display.BitmapDisplayer;
import com.example.administrator.myapplication.classDemo.imageloader.utils.Constants;

import java.io.IOException;
import java.io.InputStream;

public class BaseImageDecoder implements ImageDecoder {

    @Override
    public Bitmap decode(ImageDecoding imageDecoding) throws IOException {
        Bitmap decodeBitmap;
        //ImageSize imageSize;
        InputStream imageInputStream = getInputStream(imageDecoding);
        if(imageInputStream == null){
            Log.e(Constants.LOG_TAG,"该图片无流："+imageDecoding.getCacheKey());
            return null;
        }
        try {
            decodeBitmap = BitmapFactory.decodeStream(imageInputStream);
        }finally {
            imageInputStream.close();
        }
        return decodeBitmap;
    }

    private InputStream getInputStream(ImageDecoding imageDecoding) throws IOException{
        return imageDecoding.getImageDownloader().getStream(imageDecoding.getLoadUrl());
    }
}
