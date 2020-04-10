package com.example.library;

import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.util.Log;

public class NetworkCallBackImpl extends ConnectivityManager.NetworkCallback {

    @Override
    public void onAvailable(Network network) {
        super.onAvailable(network);
        Log.e(Constants.LOG_TAG,"网络已连接");

    }

    @Override
    public void onLost(Network network) {
        super.onLost(network);
        Log.e(Constants.LOG_TAG,"good work:网络已中断");

    }

    @Override
    public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
        super.onCapabilitiesChanged(network, networkCapabilities);
        if(networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)){
            if(networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)){
                Log.e(Constants.LOG_TAG,"good work:网络发生变更，类型为：WIFI");
            }else {
                Log.e(Constants.LOG_TAG,"good work:网络类型发生变更，类型为其他");
            }
        }


    }
}
