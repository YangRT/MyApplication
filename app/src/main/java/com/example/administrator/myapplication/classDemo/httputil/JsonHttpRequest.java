package com.example.administrator.myapplication.classDemo.httputil;
import android.net.Uri;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class JsonHttpRequest implements IHttpRequest {

    private String url;
    private byte[] data;
    private CallbackListener listener;
    private HttpURLConnection connection;


    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public void setListener(CallbackListener listener) {
        this.listener = listener;
    }

    @Override
    public void execute() {
        URL uri;
        try {
            uri = new URL(this.url);
            connection = (HttpURLConnection)uri.openConnection();
            connection.setConnectTimeout(6000);
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setReadTimeout(6000);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type","application/json;charset=utf-8");
            connection.connect();
            OutputStream outputStream = connection.getOutputStream();
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
            bufferedOutputStream.write(data);
            bufferedOutputStream.flush();
            outputStream.close();
            bufferedOutputStream.close();
            if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                InputStream in = connection.getInputStream();
                listener.onSuccess(in);
            }else {
                throw new RuntimeException("请求失败！！！！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("请求失败！！！！");
        }finally {
            connection.disconnect();
        }
    }
}
