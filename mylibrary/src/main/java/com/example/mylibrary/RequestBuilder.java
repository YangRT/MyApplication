package com.example.mylibrary;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.RequestBody;

//最终请求拼装类
public class RequestBuilder {

    //方法请求方式
    private String method;
    //接口请求地址
    private HttpUrl baseUrl;
    //方法注解值
    private String relativeUrl;
    //
    private HttpUrl.Builder urlBuilder;
    private FormBody.Builder formBuilder;
    private Request.Builder requestBuilder;

    public RequestBuilder(String method, HttpUrl baseUrl, String relativeUrl,boolean hasBody) {
        this.method = method;
        this.baseUrl = baseUrl;
        this.relativeUrl = relativeUrl;

        requestBuilder = new Request.Builder();
        if(hasBody) formBuilder = new FormBody.Builder();
    }

    void addQueryParam(String name,String value){
        if(relativeUrl != null){
            urlBuilder = baseUrl.newBuilder(relativeUrl);
            if(urlBuilder == null){
                throw new IllegalArgumentException("Malformed URL. Base"+baseUrl+",Relative:"+relativeUrl);
            }
            relativeUrl = null;
        }
        urlBuilder.addQueryParameter(name,value);
    }

    void addFormField(String name,String value) {
        formBuilder.add(name,value);
    }

    Request build(){
        HttpUrl url;
        if(urlBuilder != null){
            url = urlBuilder.build();
        }else {
            url = baseUrl.resolve(relativeUrl);
            if(url == null){
                throw new IllegalArgumentException("Malformed URL. Base"+baseUrl+",Relative:"+relativeUrl);
            }
        }

        RequestBody body = null;
        if(formBuilder != null){
            body = formBuilder.build();
        }
        return requestBuilder.url(url).method(method,body).build();
    }
}
