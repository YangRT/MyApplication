package com.example.administrator.myapplication.classDemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.classDemo.imageloader.utils.Constants;
import com.example.library.NetChangeObserver;
import com.example.library.NetType;
import com.example.library.Network;
import com.example.library.NetworkManager;

public class Main4Activity extends AppCompatActivity  {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        NetworkManager.getInstance().registerObserver(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        NetworkManager.getInstance().ubRegisterObserver(this);
    }

    @Network(netType = NetType.WIFI)
    public void network(NetType netType){
        switch (netType){
            case WIFI:
                Log.e(Constants.LOG_TAG,"Main4Activity >>> wifi");
                break;
            case CMNET:
            case CMMAP:
                //流量网络
                Log.e(Constants.LOG_TAG,"Main4Activity >>> "+netType.name());
                break;
            case NONE:
                //没有网络 提示用户，跳转到设置页面
                Log.e(Constants.LOG_TAG,"Main4Activity >>> 没有网络");
                break;
        }
    }

}
