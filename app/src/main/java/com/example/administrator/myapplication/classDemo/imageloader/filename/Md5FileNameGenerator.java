package com.example.administrator.myapplication.classDemo.imageloader.filename;

import android.util.Log;

import com.example.administrator.myapplication.classDemo.imageloader.utils.Constants;

import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5FileNameGenerator implements FileNameGenerator {

    private static final String HASH_ALGORITHM = "MD5";
    private static final int RADIX = 10 + 26; //10位数字 + 26个字母

    @Override
    public String generate(String imageUrl) {
        byte[] md5 = getMD5(imageUrl.getBytes());
        BigInteger bi = new BigInteger(md5).abs();
        return bi.toString(RADIX);
    }

    private byte[] getMD5(byte[] bytes) {
        byte[] hash = null;
        try{
            MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
            digest.update(bytes);
            hash = digest.digest();
        } catch (NoSuchAlgorithmException e) {
            Log.e(Constants.LOG_TAG,e.getMessage());
            e.printStackTrace();
        }
        return hash;
    }
}
