package com.example.administrator.myapplication.classDemo.imageloader.downloader;

import java.io.IOException;
import java.io.InputStream;

public interface ImageDownloader {

    InputStream getStream(String imageUrl) throws IOException;

    enum Scheme{
        HTTP("http"),HTTPS("https"),FILE("file"),UNKNOWN("");

        private String scheme;
        private String urlPrefix;

        Scheme(String scheme){
            this.scheme = scheme;
            urlPrefix = scheme + "://";
        }

        public static Scheme ofUrl(String url){
            String[] s = url.split(":");
            String t = s[0];
            switch (t){
                case "http":
                    return Scheme.HTTP;
                case "https":
                    return Scheme.HTTPS;
                case "file":
                    return Scheme.FILE;
                default:
                    return Scheme.UNKNOWN;
            }
        }
    }
}
