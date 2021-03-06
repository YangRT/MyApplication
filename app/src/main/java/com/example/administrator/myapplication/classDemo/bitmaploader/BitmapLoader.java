package com.example.administrator.myapplication.classDemo.bitmaploader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.StatFs;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

import com.example.administrator.myapplication.R;
import com.jakewharton.disklrucache.DiskLruCache;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class BitmapLoader {

    private static final String TAG = "BitmapLoader";

    private static final int MESSAGE_POST_RESULT = 1;

    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = CPU_COUNT + 1;
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
    private static final long KEEP_ALIVE = 10L;

    private static final int TAG_KEY_URL = R.id.bitmaploader_url;
    private static final long DISK_CACHE_SIZE = 1024 * 1024 * 50;
    private static final int IO_BUFFER_SIZE = 8 * 1024;
    private static final int DISK_CACHE_INDEX = 0;
    private boolean mIsDiskLruCacheCreated = false;

    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
       private final AtomicInteger mCount = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r,"BitmapLoader#"+mCount.getAndIncrement());
        }
    } ;

    public static final Executor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(CORE_POOL_SIZE,MAXIMUM_POOL_SIZE,KEEP_ALIVE, TimeUnit.SECONDS,new LinkedBlockingQueue<Runnable>(),sThreadFactory);

    private Handler mMainHandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            LoaderResult result = (LoaderResult)msg.obj;
            ImageView imageView = result.imageView;
            String url = (String) imageView.getTag(TAG_KEY_URL);
            if(url.equals(result.url)){
                imageView.setImageBitmap(result.bitmap);
            }else {
                Log.w(TAG,"see image bitmap,but url has changed,ignored!");
            }
        }
    } ;

    private Context mContext;
    private ImageResizer mImageResizer = new ImageResizer();
    private LruCache<String,Bitmap> mMemoryCache;
    private DiskLruCache mDiskLruCache;
    private boolean isLocalImage = false;

    private BitmapLoader(Context context,boolean isLocalImage){
        this.isLocalImage = isLocalImage;
        mContext = context.getApplicationContext();
        int maxMemory = (int)(Runtime.getRuntime().maxMemory() / 1024);
        int cacheSize = maxMemory / 6;
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight() / 1024;
            }
        };
        File diskCacheDir = getDiskCahcheDir(mContext,"bitmap");
        if(!diskCacheDir.exists()){
            diskCacheDir.mkdirs();
        }
        if(getUsableSpace(diskCacheDir) > DISK_CACHE_SIZE){
            try{
                mDiskLruCache = DiskLruCache.open(diskCacheDir,1,1,DISK_CACHE_SIZE);
                mIsDiskLruCacheCreated = true;
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public static  BitmapLoader build(Context context,boolean isLocalImage){
        return new BitmapLoader(context,isLocalImage);
    }

    private void addBitmapToMemoryCache(String key,Bitmap bitmap){
        if(getBitmapFromMemCache(key) == null){
            mMemoryCache.put(key,bitmap);
        }
    }

    private Bitmap getBitmapFromMemCache(String key){
        return mMemoryCache.get(key);
    }

    public void bindBitmap(final String url,final ImageView imageView){
        bindBitmap(url,imageView,0,0);
    }

    public void bindBitmap(final String url,final ImageView imageView,final int reqWidth,final int reHeight){
        Log.d(TAG,"w:"+reqWidth+" h:"+reHeight);
        imageView.setTag(TAG_KEY_URL,url);
        Bitmap bitmap = loadBitmapFromMemCache(url);
        if(bitmap != null) {
            imageView.setImageBitmap(bitmap);
            return;
        }
        Runnable loadBitmapTask = new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = loadBitmap(url,reqWidth,reHeight);
                if(bitmap != null){
                    LoaderResult result = new LoaderResult(imageView,url,bitmap);
                    mMainHandler.obtainMessage(MESSAGE_POST_RESULT,result).sendToTarget();
                }
            }
        };
        THREAD_POOL_EXECUTOR.execute(loadBitmapTask);
    }

    public Bitmap loadBitmap(String url,int reqWidth,int reqHeight){
        Bitmap bitmap = loadBitmapFromMemCache(url);
        if(bitmap !=  null){
            Log.d(TAG,"loadBitmapFromMemCache,url:"+url);
            return bitmap;
        }

        try {
            bitmap = loadBitmapFromDiskCache(url,reqWidth,reqHeight);
            if(bitmap != null){
                Log.d(TAG,"loadBitmapFromDiskCache,url:"+url);
                return bitmap;
            }
            if(isLocalImage){
                bitmap = loadBitmapFromLocal(url,reqWidth,reqHeight);
                Log.d(TAG,"loadBitmapFromHttp,url:"+url);
            }else {
                bitmap = loadBitmapFromHttp(url,reqWidth,reqHeight);
                Log.d(TAG,"loadBitmapFromHttp,url:"+url);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        if(bitmap == null && !mIsDiskLruCacheCreated){
            Log.w(TAG,"encounter error,DiskLruCache is not created.");
            bitmap = downloadBitmapFromUrl(url);
        }
        return bitmap;
    }

    private Bitmap loadBitmapFromLocal(String url, int reqWidth, int reqHeight) throws IOException{
        if(Looper.myLooper() == Looper.getMainLooper()){
            throw new RuntimeException("can not visit network from UI thread");
        }
        Bitmap bitmap = null;
        String key = hashKeyFormUrl(url);
        bitmap = mImageResizer.decodeSampledBitmapFromLocal(url,reqWidth,reqHeight);
        if(bitmap != null){
            addBitmapToMemoryCache(key,bitmap);
        }
        return bitmap;
    }



    private Bitmap loadBitmapFromMemCache(String url){
        final String key = hashKeyFormUrl(url);
        Bitmap bitmap = getBitmapFromMemCache(key);
        return bitmap;
    }

    private Bitmap loadBitmapFromHttp(String url,int reqWidth,int reqHeight) throws IOException{
        if(Looper.myLooper() == Looper.getMainLooper()){
            throw new RuntimeException("can not visit network from UI thread");
        }
        if(mDiskLruCache == null){
            return null;
        }
        String key = hashKeyFormUrl(url);
        DiskLruCache.Editor editor = mDiskLruCache.edit(key);
        if(editor != null){
            OutputStream outputStream = editor.newOutputStream(DISK_CACHE_INDEX);
            if(downloadUrlToStream(url,outputStream)){
                editor.commit();
            }else {
                editor.abort();
            }
            mDiskLruCache.flush();
        }
        return loadBitmapFromDiskCache(url,reqWidth,reqHeight);
    }

    private Bitmap loadBitmapFromDiskCache(String url,int reqWidth,int reqHeight) throws IOException{
        if(Looper.myLooper() == Looper.getMainLooper()){
            Log.w(TAG,"load bitmap from UI Thread,it is not recommended");
        }
        if(mDiskLruCache == null){
            return null;
        }
        Bitmap bitmap = null;
        String key = hashKeyFormUrl(url);
        DiskLruCache.Snapshot snapshot = mDiskLruCache.get(key);
        if(snapshot != null){
            FileInputStream fileInputStream = (FileInputStream)snapshot.getInputStream(DISK_CACHE_INDEX);
            FileDescriptor descriptor = fileInputStream.getFD();
            bitmap = mImageResizer.decodeSampledBitmapFromDescriptor(descriptor,reqWidth,reqHeight);
            if(bitmap != null){
                addBitmapToMemoryCache(key,bitmap);
            }
        }
        return bitmap;
    }

    public boolean downloadUrlToStream(String urlString,OutputStream outputStream){
        HttpURLConnection connection = null;
        BufferedOutputStream out = null;
        BufferedInputStream in = null;
        try{
            final URL url = new URL(urlString);
            connection = (HttpURLConnection)url.openConnection();
            in = new BufferedInputStream(connection.getInputStream(),IO_BUFFER_SIZE);
            out = new BufferedOutputStream(outputStream,IO_BUFFER_SIZE);

            int b;
            while ((b= in.read())!=-1){
                out.write(b);
            }
            return true;
        }catch (IOException e){
            Log.e(TAG,"download bitmap failed");
        }finally {
            if(connection != null){
                connection.disconnect();
            }
                try {
                    if(out != null) out.close();
                    if(in != null) in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return false;
    }

    private Bitmap downloadBitmapFromUrl(String urlString){
        Bitmap bitmap = null;
        HttpURLConnection urlConnection = null;
        BufferedInputStream in = null;

        try {
            final URL url = new URL(urlString);
            urlConnection = (HttpURLConnection)url.openConnection();
            in = new BufferedInputStream(urlConnection.getInputStream(),IO_BUFFER_SIZE);
            bitmap = BitmapFactory.decodeStream(in);
        }catch (final IOException e){
            Log.e(TAG,"Error in downlaodBitmap:"+e);
        }finally {
            if(urlConnection != null){
                urlConnection.disconnect();
            }
            try {
                if(in != null) in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

    private String hashKeyFormUrl(String url){
        String cacheKey;
        try {
            final MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(url.getBytes());
            cacheKey = bytesToHexString(messageDigest.digest());
        }catch (NoSuchAlgorithmException e){
            cacheKey = String.valueOf(url.hashCode());
        }
        return cacheKey;
    }

    private String bytesToHexString(byte[] bytes){
        StringBuilder builder = new StringBuilder();
        for(int i = 0;i < bytes.length;i++){
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if(hex.length() == 1){
                builder.append('0');
            }
            builder.append(hex);
        }
        return builder.toString();
    }

    public File getDiskCahcheDir(Context context,String uniqueName){
        boolean externalStorageAvailable = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        final String cachePath;
        if(externalStorageAvailable){
            cachePath = context.getExternalCacheDir().getPath();
        }else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath+File.separator+uniqueName);
    }

    private long getUsableSpace(File path){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD){
            return path.getUsableSpace();
        }
        final StatFs stats = new StatFs(path.getPath());
        return stats.getBlockSizeLong() * stats.getAvailableBlocksLong();
    }

    private static class LoaderResult{
        public ImageView imageView;
        public String url;
        public Bitmap bitmap;

        public LoaderResult(ImageView imageView,String url,Bitmap bitmap){
            this.imageView = imageView;
            this.url = url;
            this.bitmap = bitmap;
        }
    }

    public void bindBitmap(Uri imageUri,ImageView imageView){
        Log.d(TAG,"w:0"+" h:0");
        imageView.setTag(TAG_KEY_URL,imageUri.toString());
        Bitmap bitmap = loadBitmapFromMemCache(imageUri.toString());
        if(bitmap != null) {
            imageView.setImageBitmap(bitmap);
            return;
        }
//        Runnable loadBitmapTask = new Runnable() {
//            @Override
//            public void run() {
//                Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver());
//                if(bitmap != null){
//                    LoaderResult result = new LoaderResult(imageView,url,bitmap);
//                    mMainHandler.obtainMessage(MESSAGE_POST_RESULT,result).sendToTarget();
//                }
//            }
//        };
//        THREAD_POOL_EXECUTOR.execute(loadBitmapTask);
    }
}
