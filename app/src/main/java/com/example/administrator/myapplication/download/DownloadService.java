package com.example.administrator.myapplication.download;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.example.administrator.myapplication.R;

import java.io.File;
//后台执行
public class DownloadService extends Service {
    private DownloadTask task;
    private String downloadUrl;

    private DownloadListener listener = new DownloadListener() {
        @Override
        public void onProgress(int progress) {
            getNotificationManager().notify(1,getNotification("Downloading...",progress));
        }

        @Override
        public void onSuccess() {
            task = null;
            stopForeground(true);
            getNotificationManager().notify(1,getNotification("Download Success",-1));
            Toast.makeText(DownloadService.this,"Download success",Toast.LENGTH_LONG).show();
        }

        @Override
        public void onFailed() {
            task = null;
            stopForeground(true);
            getNotificationManager().notify(1,getNotification("Download Failed",-1));
            Toast.makeText(DownloadService.this,"Download failed",Toast.LENGTH_LONG).show();

        }

        @Override
        public void onPaused() {
            task = null;
            Toast.makeText(DownloadService.this,"Paused",Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCanceled() {
            task = null;
            stopForeground(true);
            Toast.makeText(DownloadService.this,"Canceled",Toast.LENGTH_LONG).show();
        }
    };

    private DownloadBinder mBinder = new DownloadBinder();

    class DownloadBinder extends Binder{

        public void startDownload(String url){
            if(task == null){
                downloadUrl = url;
                task = new DownloadTask(listener);
                task.execute(url);
                getNotificationManager().notify(1,getNotification("Downloading",0));
                Toast.makeText(DownloadService.this,"Downloading...",Toast.LENGTH_LONG).show();
            }
        }
        public void pauseDownload(){
            if(task != null){
                task.pauseDownload();
            }
        }
        public void cancelDownload(){
            if(task != null){
                task.cancelDownload();
            }
            if(downloadUrl != null){
                String fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/"));
                String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
                File file = new File(directory+fileName);
                if(file.exists()){
                    file.delete();
                }
                getNotificationManager().cancel(1);
                stopForeground(true);
                Toast.makeText(DownloadService.this,"Canceled",Toast.LENGTH_LONG).show();
            }
        }

    }


    public DownloadService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;

    }

    private NotificationManager getNotificationManager(){
       NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT >= 26){
            String channelID = "1";
            String channelName = "channel_name";
            NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(channel);
        }
        return manager;
    }

    private Notification getNotification(String title,int progress){
        Intent intent = new Intent(this,DownloadActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher));
        builder.setContentIntent(pendingIntent);
        builder.setContentTitle(title);
        if(progress >= 0){
            builder.setContentText(progress+"%");
            builder.setProgress(100,progress,false);
        }
        if(Build.VERSION.SDK_INT >= 26){
            String channelID = "1";
            String channelName = "channel_name";
            NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);
            builder.setChannelId(channelID);
        }
        return builder.build();
    }

}
