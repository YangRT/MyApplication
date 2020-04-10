package com.example.mylibrary;

import com.example.mylibrary.http.Field;
import com.example.mylibrary.http.GET;
import com.example.mylibrary.http.POST;
import com.example.mylibrary.http.Query;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.annotation.Annotation;

import okhttp3.Call;
import okhttp3.HttpUrl;

public class ServiceMethod {

    //OkhttpClient唯一实现接口
    private final Call.Factory callFactory;
    //接口请求地址
    private final HttpUrl baseUrl;
    //方法的注解方式（如:GET,POST）
    private final String httpMethod;
    //方法的注解值 如（“/ip/ipNew”)
    private final String relativeUrl;
    //方法参数的数组(每个对象包含：参数注解值，参数值）
    private final ParameterHandler[] parameterHandlers;
    //是否有请求体
    private final boolean hasBody;


    private ServiceMethod(Builder builder){
        this.callFactory = builder.retrofit.callFactory();
        this.baseUrl = builder.retrofit.baseUrl();
        this.httpMethod = builder.httpMethod;
        this.relativeUrl = builder.relativeUrl;
        this.parameterHandlers = builder.parameterHandlers;
        this.hasBody = builder.hasBody;
    }

    public Call toCall(Object[] args) {
        //拼装
        RequestBuilder requestBuilder = new RequestBuilder(httpMethod,baseUrl,relativeUrl,hasBody);
        ParameterHandler[] handlers = this.parameterHandlers;
        //检测：自己收集的匹配args
        int argumentCount = args != null ? args.length : 0;
        if(argumentCount != handlers.length){
            throw new IllegalArgumentException("自己收集的参数错误！");
        }
        for (int i = 0;i < argumentCount;i++){
            handlers[i].apply(requestBuilder,args[i].toString());
        }
        return callFactory.newCall(requestBuilder.build());
    }


    static final class Builder{
        //OkhttpClient 构建对象
        final Retrofit retrofit;
        //带注解的方法
        final Method method;
        //方法的所有注解
        final Annotation[] methodAnnotation;
        //方法参数的所有注解
        Annotation[][] paramsAnnotationArray;
        //方法的注解方式（如:GET,POST）
        private String httpMethod;
        //方法的注解值 如（“/ip/ipNew”)
        private String relativeUrl;
        //方法参数的数组(每个对象包含：参数注解值，参数值）
        private ParameterHandler[] parameterHandlers;
        //是否有请求体
        private boolean hasBody;

         Builder(Retrofit retrofit,Method method){
            this.retrofit = retrofit;
            this.method = method;
            //获取方法的所有注解
            this.methodAnnotation = method.getAnnotations();
            //获取方法参数的所有注解
            this.paramsAnnotationArray = method.getParameterAnnotations();
        }

        ServiceMethod build(){
            for(Annotation annotation:methodAnnotation){
                parseMethodAnnotation(annotation);
            }
            int parameterCount = paramsAnnotationArray.length;
            parameterHandlers = new ParameterHandler[parameterCount];
            for(int i = 0;i < parameterCount;i++){
                Annotation[] parameterAnnotations = paramsAnnotationArray[i];
                if(parameterAnnotations == null){
                    throw new IllegalArgumentException("Retrofit annnotation not found!");
                }
                //赋值
                parameterHandlers[i] = parseParameter(parameterAnnotations);
            }

            return new ServiceMethod(this);
        }

        //解析参数的所有注解
        private ParameterHandler parseParameter(Annotation[] parameterAnnotations) {
            ParameterHandler result = null;
            //遍历参数注解
            for(Annotation annotation:parameterAnnotations){
                ParameterHandler annotationAction = parseParameterAnnotation(annotation);
            }
            return result;
         }

        private ParameterHandler parseParameterAnnotation(Annotation annotation) {
            if(annotation instanceof Query){
                Query query = (Query) annotation;
                String name = query.value();
                return new ParameterHandler.Query(name);
            }else if(annotation instanceof Field){
                Field field = (Field) annotation;
                String name = field.value();
                return new ParameterHandler.Field(name);
            }
            return null;
         }

        private void parseMethodAnnotation(Annotation annotation) {
            if(annotation instanceof GET){
                parseHttpMethodAndPath("GET",((GET)annotation).value(),false);
            }else if(annotation instanceof POST){
                parseHttpMethodAndPath("POST",((POST)annotation).value(),true);
            }
        }

        private void parseHttpMethodAndPath(String httpMethod, String value, boolean hasBody) {
            //方法请求方式
            this.httpMethod = httpMethod;
            this.relativeUrl = value;
            this.hasBody = hasBody;

        }



    }
}
