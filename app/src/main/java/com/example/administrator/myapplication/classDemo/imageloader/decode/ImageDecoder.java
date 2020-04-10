package com.example.administrator.myapplication.classDemo.imageloader.decode;

import android.graphics.Bitmap;

import com.example.administrator.myapplication.classDemo.imageloader.bean.ImageDecoding;

import java.io.IOException;

public interface ImageDecoder {
    Bitmap decode(ImageDecoding imageDecoding) throws IOException;
}
