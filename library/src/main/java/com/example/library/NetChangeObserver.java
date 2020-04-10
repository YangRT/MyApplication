package com.example.library;

public interface NetChangeObserver {

    void onConnect(NetType type);

    void onDisConnect();
}
