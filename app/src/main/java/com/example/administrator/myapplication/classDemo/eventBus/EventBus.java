package com.example.administrator.myapplication.classDemo.eventBus;

import android.os.Handler;
import android.os.Looper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
//手写EventBus
public class EventBus {
    private static volatile EventBus instance;
    private Map<Object,List<SubscribeMethod>> cacheMap;//保存所有方法
    private Handler mHandler;
    private ExecutorService mExecutorService;

    private EventBus(){
        cacheMap = new HashMap<>();
        mHandler = new Handler(Looper.getMainLooper());
        mExecutorService = Executors.newCachedThreadPool();
    }

    public static EventBus getDefault(){
        if(instance == null){
            synchronized (EventBus.class){
                if(instance == null){
                    instance = new EventBus();
                }
            }
        }
        return instance;
    }

    public  void register(Object object){
        List<SubscribeMethod> list = cacheMap.get(object);
        if(list == null){
            list = findSubscribeMethods(object);
            cacheMap.put(object,list);
        }
    }

    private List<SubscribeMethod> findSubscribeMethods(Object object) {
        List<SubscribeMethod> list = new ArrayList<>();
        Class<?> clazz = object.getClass();
        //循环查找父类是否存在注解方法
        while (clazz != null){

            //判断是否为系统类，是就退出
            String name = clazz.getName();
            if(name.startsWith("java.") || name.startsWith("javax.")|| name.startsWith("android.")){
                break;
            }

            //得到所有方法
            Method[] methods = clazz.getMethods();
            for(Method method: methods){
                //通过注解找到注册的方法
                Subscribe subscribe = method.getAnnotation(Subscribe.class);
                if(subscribe == null) continue;
                //获取方法参数并判断
                Class<?>[] types = method.getParameterTypes();
                if(types.length != 1){
                    throw new RuntimeException("EventBus只接受一个参数的方法");
                }
                //获取线程模式
                ThreadMode mode = subscribe.threadMode();
                SubscribeMethod subscribeMethod = new SubscribeMethod(method,mode,types[0]);
                list.add(subscribeMethod);
            }
            clazz = clazz.getSuperclass();
        }

        return list;
    }
    public void post(final Object type){
        Set<Object> set = cacheMap.keySet();
        Iterator<Object> iterator = set.iterator();
        while (iterator.hasNext()){
            final Object obj = iterator.next();
            List<SubscribeMethod> list = cacheMap.get(obj);
            for(final SubscribeMethod subscribeMethod: list){
                //两个对象的类信息进行比较，前者是后者的父类或父接口
                if(subscribeMethod.getType().isAssignableFrom(type.getClass())){
                    //主线程
                    switch (subscribeMethod.getmThreadMode()){
                        case MAIN:
                            // 主线程 - 主线程  子线程-主线程
                            if(Looper.myLooper() == Looper.getMainLooper()){
                                invoke(subscribeMethod,obj,type);
                            }else{
                                mHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        invoke(subscribeMethod,obj,type);
                                    }
                                });
                            }
                            break;
                        case BACKGROUND:
                            //主线程 - 子线程  子线程 - 子线程
                            if(Looper.myLooper() == Looper.getMainLooper()){
                                mExecutorService.execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        invoke(subscribeMethod,obj,type);
                                    }
                                });
                            }else{
                                invoke(subscribeMethod,obj,type);
                            }
                            break;
                    }
                }
            }
        }
    }

    private void invoke(SubscribeMethod subscribeMethod, Object obj, Object type) {
        Method method = subscribeMethod.getmMethod();
        try {
            method.invoke(obj,type);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
