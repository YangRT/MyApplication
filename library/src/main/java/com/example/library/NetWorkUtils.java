package com.example.library;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

public class NetWorkUtils {
    //监听网络是否可用
    public static boolean isNetworkAvailable(){
        ConnectivityManager manager = (ConnectivityManager) NetworkManager.getInstance().getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        if(manager == null){
            return false;
        }
        NetworkInfo[] infos = manager.getAllNetworkInfo();
        if(infos != null){
            for(NetworkInfo info:infos){
                if(info.getState() == NetworkInfo.State.CONNECTED){
                    return true;
                }
            }
        }

        return false;
    }

    //当前网络类型
    public static NetType getType(){
        ConnectivityManager manager = (ConnectivityManager) NetworkManager.getInstance().getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        if(manager == null){
            return NetType.NONE;
        }
        NetworkInfo info = manager.getActiveNetworkInfo();
        if(info == null){
            return NetType.NONE;
        }
        int nType = info.getType();
        if(nType == ConnectivityManager.TYPE_MOBILE){
            if(info.getExtraInfo().toLowerCase().equals("cmnet")){
                return NetType.CMNET;
            }else {
                return NetType.CMMAP;
            }
        }else if(nType == ConnectivityManager.TYPE_WIFI){
            return NetType.WIFI;
        }
        return NetType.NONE;
    }
    //打开网络设置界面
    public static void openSetting(Context context,int requestCode){
        Intent intent = new Intent("/");
        ComponentName componentName = new ComponentName("com.android.settings","com.android.settings.WirelessSettings");
        intent.setComponent(componentName);
        intent.setAction("android.intent.action.VIEW");
        ((Activity)context).startActivityForResult(intent,requestCode);
    }



}
