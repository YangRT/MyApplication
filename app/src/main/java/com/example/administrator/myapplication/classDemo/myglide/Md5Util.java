package com.example.administrator.myapplication.classDemo.myglide;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Util {
    private static MessageDigest digest;
    static {
        try {
            digest = MessageDigest.getInstance("MD5");
        }catch (NoSuchAlgorithmException e){

        }
    }

    public static String toMD5(String key){
        if(digest == null){
            return String.valueOf(key.hashCode());
        }
        digest.update(key.getBytes());
        return convert2HexString(digest.digest());
    }

    private static String convert2HexString(byte[] bytes){
        StringBuffer sb = new StringBuffer();
        for(byte b:bytes){
            String hex = Integer.toHexString(0xFF & b);
            if(hex.length() == 1){
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }
}
