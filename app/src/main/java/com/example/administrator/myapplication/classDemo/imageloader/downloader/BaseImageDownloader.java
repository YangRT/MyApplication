package com.example.administrator.myapplication.classDemo.imageloader.downloader;

import android.content.Context;
import android.net.Uri;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import javax.xml.validation.Schema;

import static com.example.administrator.myapplication.classDemo.imageloader.downloader.ImageDownloader.Scheme.*;

public class BaseImageDownloader implements ImageDownloader {

    private Context context;
    //连接超时15秒
    private static final int DEFAULT_HTTP_CONNECT_TIMEOUT = 15 * 1000;
    //读取超时15秒
    private static final int DEFAULT_HTTP_READ_TIMEOUT = 15 * 1000;
    //连接超时次数
    private final int connectTimeout;
    //读取超时次数
    private final int readTimeout;
    //以字节为单位的缓冲区大小
    private static final int BUFFER_SIZE = 32 * 1024;
    //允许扩展字符集
    private static final String ALLOWED_URL_CHARS = "@#&=*-_.,:!?()/~%";

    public BaseImageDownloader(Context context){
        this(context,DEFAULT_HTTP_CONNECT_TIMEOUT,DEFAULT_HTTP_READ_TIMEOUT);
    }

    private BaseImageDownloader(Context context,int connectTimeout,int readTimeout){
        this.context = context;
        this.connectTimeout = connectTimeout;
        this.readTimeout = readTimeout;
    }
    @Override
    public InputStream getStream(String imageUrl) throws IOException {
        switch (ofUrl(imageUrl)){
            case HTTP:
            case HTTPS:
               return getStreamFromNetWork(imageUrl);
            case FILE:
                return getStreamFromFiles(imageUrl);
            case UNKNOWN:
                return getInputStreamFromOtherSource(imageUrl);
        }
        return null;
    }

    private InputStream getStreamFromFiles(String imageUrl) throws IOException{
        BufferedInputStream stream = new BufferedInputStream(new FileInputStream(imageUrl),BUFFER_SIZE);
        return stream;
    }

    private InputStream getInputStreamFromOtherSource(String imageUrl) {
        throw new UnsupportedOperationException("图像URL："+imageUrl+"不支持定义的Scheme");
    }

    private InputStream getStreamFromNetWork(String imageUrl) throws IOException{
        String encodedUrl = Uri.encode(imageUrl,ALLOWED_URL_CHARS);
        HttpURLConnection connection = (HttpURLConnection) new URL(encodedUrl).openConnection();
        connection.setReadTimeout(readTimeout);
        connection.setConnectTimeout(connectTimeout);

        InputStream stream;
        try {
            stream = connection.getInputStream();
        }catch (IOException e){
            stream = connection.getErrorStream();
            stream.close();
            throw e;
        }
        if(connection.getResponseCode() != 200){
            stream.close();
            throw new IOException("网络请求图片失败，返回标识码为："+connection.getResponseCode());
        }
        return new BufferedInputStream(stream,BUFFER_SIZE);

    }
}
