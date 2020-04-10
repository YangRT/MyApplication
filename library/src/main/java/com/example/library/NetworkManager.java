package com.example.library;

import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkRequest;
import android.os.Build;

public class NetworkManager {

    private static volatile NetworkManager manager;
    private NetStateReceiver receiver;
    private Application application;

    public static NetworkManager getInstance(){
        if(manager == null){
            synchronized (NetworkManager.class){
                if(manager == null){
                    manager = new NetworkManager();
                }
            }
        }
        return manager;
    }



    private NetworkManager(){
        receiver = new NetStateReceiver();
    }

    public Application getApplication(){
        return application;
    }

    public void init(Application application){
        this.application = application;
        //动态广播注册
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.ANDROID_NET_CHANGE_ACTION);
        application.registerReceiver(receiver,filter);

//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
//            ConnectivityManager.NetworkCallback callback = new NetworkCallBackImpl();
//            NetworkRequest.Builder builder = new NetworkRequest.Builder();
//            NetworkRequest request = builder.build();
//            ConnectivityManager manager = (ConnectivityManager)NetworkManager.getInstance().getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
//            if(manager != null) manager.registerNetworkCallback(request,callback);
//        }else{
//
//        }
    }

    public void registerObserver(Object register){
        receiver.registerObserver(register);
    }

    public void ubRegisterObserver(Object register){
        receiver.unRegisterObserver(register);
    }
}
