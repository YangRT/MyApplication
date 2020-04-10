package com.example.administrator.myapplication.classDemo.httputil;
import android.provider.Settings;
import com.alibaba.fastjson.JSON;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class HttpTask<T> implements Runnable, Delayed {

    private IHttpRequest request;

    public HttpTask(String url,T requestData,IHttpRequest request,CallbackListener listener){
        this.request = request;
        request.setUrl(url);
        request.setListener(listener);
        String data = JSON.toJSONString(requestData);
        request.setData(data.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public void run() {
        try {
            request.execute();
        }catch (Exception e){
            ThreadPoolManager.getInstance().addDelayTask(this);
        }

    }

    public long getDelayTime() {
        return delayTime;
    }

    public void setDelayTime(long delayTime) {
        this.delayTime = System.currentTimeMillis()+delayTime;
    }

    private long delayTime;

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    private int retryCount;

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(this.delayTime-System.currentTimeMillis(),TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        return 0;
    }
}
