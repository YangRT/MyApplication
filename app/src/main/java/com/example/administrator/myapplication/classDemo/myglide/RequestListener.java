package com.example.administrator.myapplication.classDemo.myglide;

import android.graphics.Bitmap;

public interface RequestListener {

    Boolean onSuccess(Bitmap bitmap);

    Boolean onFailure();
}
