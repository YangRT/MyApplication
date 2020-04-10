package com.example.library;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.ArrayMap;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class NetStateReceiver extends BroadcastReceiver {

    private NetType netType;
    private Map<Object, List<MethodManager>> networkList;

    public NetStateReceiver(){
        //初始化网络
        netType = NetType.NONE;
        networkList = new HashMap<>();
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent == null || intent.getAction() == null){
            Log.e(Constants.LOG_TAG,"异常..");
            return;
        }

        //处理广播事件
        if(intent.getAction().equalsIgnoreCase(Constants.ANDROID_NET_CHANGE_ACTION)){
            Log.e(Constants.LOG_TAG,"网络发生改变");
            netType = NetWorkUtils.getType();
            if(NetWorkUtils.isNetworkAvailable()){
                Log.e(Constants.LOG_TAG,"网络连接成功");
            }else {
                Log.e(Constants.LOG_TAG,"网络连接失败");
            }
            post(netType);
        }
    }

    private void post(NetType netType) {
        Set<Object> set = networkList.keySet();
        for(Object o:set){
            List<MethodManager> methodList = networkList.get(o);
            if(methodList != null){
                for(MethodManager method:methodList){
                    //两者参数比较
                    if(method.getType().isAssignableFrom(netType.getClass())){
                        switch (method.getNetType()){
                            case AUTO:
                                invoke(method,o,netType);
                                break;
                            case WIFI:
                                if(netType == NetType.WIFI || netType == NetType.NONE){
                                    invoke(method,o,netType);
                                }
                                break;
                            case CMMAP:
                                if(netType == NetType.CMMAP || netType == NetType.NONE){
                                    invoke(method,o,netType);
                                }
                                break;
                            case CMNET:
                                if(netType == NetType.CMMAP || netType == NetType.NONE){
                                    invoke(method,o,netType);
                                }
                                break;
                        }

                    }
                }
            }
        }
    }

    private void invoke(MethodManager methodManager,Object o,NetType netType){
        try {
            methodManager.getMethod().invoke(o,netType);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    //将应用中所有监听网络的activity中网络监听的方法添加到集合
    public void registerObserver(Object register) {

        List<MethodManager> methodList = networkList.get(register);
        if(methodList == null){
            methodList = findAnnotationMethod(register);
            networkList.put(register,methodList);
        }


    }

    //利用反射从注解方法中找到监听方法，添加到集合
    private List<MethodManager> findAnnotationMethod(Object register) {
        List<MethodManager> list = new ArrayList<>();
        Class<?> clazz = register.getClass();
        Method[] methods = clazz.getMethods();
        for(Method method:methods){
            Network network = method.getAnnotation(Network.class);
            if(network == null){
                continue;
            }
            Class<?>[] parameterTypes = method.getParameterTypes();
            if(parameterTypes.length != 1 || !parameterTypes[0].isEnum()){
                throw new RuntimeException(method.getName() + "方法有且只有一个参数");
            }

            MethodManager methodManager = new MethodManager(parameterTypes[0],network.netType(),method);
            list.add(methodManager);
        }
        return list;
    }

    public void unRegisterObserver(Object register) {
        if(networkList.isEmpty()){
            networkList.remove(register);
        }
        Log.e(Constants.LOG_TAG,register.getClass().getName()+"注销成功");


    }

}
