package com.example.library;
//网络类型
public enum NetType {
    //只有有网络，包括WiFi，流量
    AUTO,
    //WIFI网络
    WIFI,
    //主要是PC/笔记本/PDA设备  上网
    CMNET,
    // 主要是用于手机上网
    CMMAP,
    //没有网络
    NONE;


}
