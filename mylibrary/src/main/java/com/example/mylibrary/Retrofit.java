package com.example.mylibrary;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

public class Retrofit {
    //缓存集合 避免重复解读
    private final Map<Method,ServiceMethod> serviceMethodCache = new ConcurrentHashMap<>();
    //接口请求地址
    private HttpUrl baseUrl;
    //发起Http请求
    private Call.Factory callFactory;

    public Retrofit(Builder builder){
        this.baseUrl = builder.baseUrl;
        this.callFactory = builder.callFactory;
    }

    public HttpUrl baseUrl(){
        return baseUrl;
    }

    public Call.Factory callFactory(){
        return callFactory;
    }

    @SuppressWarnings("unchecked")
    public <T>T create(Class<T> service){
        return (T)Proxy.newProxyInstance(service.getClassLoader(), new Class[]{service}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
               //拦截某方法的内容：方法名，方法注解值，方法参数名
               //将这些
                ServiceMethod serviceMethod = loadServiceMethod(method);

                return new OkHttpCall(serviceMethod,args);
            }
        });
    }

    private ServiceMethod loadServiceMethod(Method method) {
        ServiceMethod result = serviceMethodCache.get(method);
        if(result != null){
            return result;
        }
        synchronized (serviceMethodCache){
            result = serviceMethodCache.get(method);
            if(result == null){
                result = new ServiceMethod.Builder(this,method).build();
                serviceMethodCache.put(method,result);
            }
        }
        return result;
    }


    public static class Builder{
        //接口请求地址
        private HttpUrl baseUrl;
        //发起Http请求
        private Call.Factory callFactory;

        //对外提供api
        public Builder baseUrl(String baseUrl){
            if(baseUrl.isEmpty()){
                throw new NullPointerException("baseUrl == null");
            }
            this.baseUrl = HttpUrl.parse(baseUrl);
            return this;
        }

        public Builder baseUrl(HttpUrl baseUrl){
            this.baseUrl = baseUrl;
            return this;
        }

        public Builder callFactory(Call.Factory callFactory){
            this.callFactory = callFactory;
            return this;
        }

        //构造者模式 好处：可以做参数校验和初始化工作
        public Retrofit build(){
            if(baseUrl == null){
                throw new NullPointerException("baseUrl == null");
            }
            if(callFactory == null){
                callFactory = new OkHttpClient();
            }
            return new Retrofit(this);
        }

    }


}
