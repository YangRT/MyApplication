package com.example.administrator.myapplication.classDemo.httputil;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import com.alibaba.fastjson.JSON;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class JsonCallbackListener<T> implements CallbackListener {

    private Class<T> responseClass;
    private JsonDatatransforListener listener;
    private Handler handler = new Handler(Looper.getMainLooper());

    public JsonCallbackListener(Class<T> responseClass,JsonDatatransforListener listener){
        this.responseClass = responseClass;
        this.listener = listener;
    }


    @Override
    public void onSuccess(InputStream stream) {
        //从流中读取数据（String，json格式) 将String转换为指定bean对象
        String response = getContent(stream);
        final T clazz =JSON.parseObject(response,responseClass);
        handler.post(new Runnable() {
            @Override
            public void run() {
                listener.onSuccess(clazz);
            }
        });

    }

    @Override
    public void onFailure() {

    }

    private String getContent(InputStream stream){
        String response = null;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder builder = new StringBuilder();
            String line = null;
            try {
                while ((line = reader.readLine()) != null) {
                    builder.append(line + "\n");
                }
            } catch (IOException e) {
                System.out.println("Error:" + e.toString());
            } finally {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return builder.toString();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return response;
        }
    }
}
