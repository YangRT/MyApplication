package com.example.administrator.myapplication.classDemo;

import android.app.Application;

import com.example.library.NetworkManager;

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        NetworkManager.getInstance().init(this);
    }
}
