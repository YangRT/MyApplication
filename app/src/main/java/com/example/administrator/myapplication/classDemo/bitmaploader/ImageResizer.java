package com.example.administrator.myapplication.classDemo.bitmaploader;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.FileDescriptor;
//图片压缩

public class ImageResizer {
    private static final String TAG = "ImageResizer";

    public ImageResizer(){}

    public Bitmap decodeSampledBitmapFromResource(Resources res,int resId,int reqWidth,int reqHeight){
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res,resId,options);
        options.inSampleSize = calculateInSampleSize(options,reqWidth,reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res,resId,options);
    }

    public Bitmap decodeSampledBitmapFromLocal(String url,int reqWidth,int reqHeight){
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(url,options);
        options.inSampleSize = calculateInSampleSize(options,reqWidth,reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(url,options);
    }


    public Bitmap decodeSampledBitmapFromDescriptor(FileDescriptor fileDescriptor,int reqWidth,int reqHeight){
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFileDescriptor(fileDescriptor,null,options);
        options.inSampleSize = calculateInSampleSize(options,reqWidth,reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFileDescriptor(fileDescriptor,null,options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reWidth, int reHeight){
        if(reHeight == 0 || reWidth == 0){
            return 1;
        }

        final int height = options.outHeight;
        final int width = options.outWidth;
        Log.d(TAG,"origin,w="+width+" h="+height);
        int inSampleSize = 1;

        if(height > reHeight || width > reWidth){
            final int halfHeight = height/2;
            final int halfWidth = width/2;
            while ((halfHeight/inSampleSize)>=reHeight&&(halfWidth/inSampleSize)>=reWidth){
                inSampleSize *= 2;
            }
        }
        Log.d(TAG,"sampleSize:"+inSampleSize);
        return inSampleSize;
    }
}
