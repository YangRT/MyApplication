package com.example.administrator.myapplication.classDemo.httputil;
import java.io.InputStream;

public interface CallbackListener {
    void onSuccess(InputStream stream);

    void onFailure();
}
