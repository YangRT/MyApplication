package com.example.administrator.myapplication.album;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.List;

public class GetAlbumPicture {

    public static String handleImageBeforeKitkat(Intent data, Context context){
        Uri uri = data.getData();
        return getImagePath(uri,null,context);

    }

    public static String handleImageOnKitkat(Intent data,Context context){
        String imagePath = null;
        Uri uri = data.getData();
        if(DocumentsContract.isDocumentUri(context,uri)){
            //如果是document类型的Uri,则通过document id 处理
            String docId = DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection,context);
            }else if("com.android.providers.download.documents".equals(uri.getAuthority())){
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://download/public_downloads"),Long.valueOf(docId));
                imagePath = getImagePath(contentUri,null,context);
            }else if("content".equalsIgnoreCase(uri.getScheme())){
                //如果是 content 类型的 uri  用普通方式处理
                imagePath = getImagePath(uri,null,context);
            }else if("file".equalsIgnoreCase(uri.getScheme())){
                //如果是 file 类型的 uri 直接获取图片路径即可
                imagePath = uri.getPath();
            }
        }
        return imagePath;
    }



    private static String getImagePath(Uri uri, String selection,Context context) {
        String path = null;
        Cursor cursor = context.getContentResolver().query(uri,null,selection,null,null);
        if(cursor != null){
            if(cursor.moveToFirst()){
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    public static List<String> getAllImagePath(Context context){
        List<String> mList = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Images.Media.DATE_ADDED + " DESC");
        String[] names = cursor.getColumnNames();
        for(String name:names){
            System.out.println(name);
        }
        while (cursor.moveToNext()){
            String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            System.out.println(path);
            mList.add(path);
        }
        cursor.close();

        return mList;
    }

}
