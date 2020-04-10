package com.example.administrator.myapplication.album;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.administrator.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class Main6Activity extends AppCompatActivity {

    public static final int CHOOSE_PHOTO = 2;
    private Button btSelect;
    List<String> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);
        btSelect = findViewById(R.id.select_photo);
        btSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(Main6Activity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(Main6Activity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                }else {
                    //queryAlbum();
                    Intent t = new Intent(Main6Activity.this,SelectPictureActivity.class);
                    startActivity(t);
                }
            }
        });
    }

    private void queryAlbum(){
        Intent i = new Intent("android.intent.action.GET_CONTENT");
        i.setType("image/*");
        startActivityForResult(i,CHOOSE_PHOTO);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //queryAlbum();
                    Intent t = new Intent(Main6Activity.this,SelectPictureActivity.class);
                    startActivity(t);
                }else {
                    Toast.makeText(this,"你拒绝了访问",Toast.LENGTH_LONG).show();
                }
            break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case CHOOSE_PHOTO:
                if(resultCode == RESULT_OK){
                    if(Build.VERSION.SDK_INT >= 19){
                        String t = GetAlbumPicture.handleImageOnKitkat(data,this);
                        if(t != null){
                            mList.add(t);
                        }
                    }else {
                        String t = GetAlbumPicture.handleImageBeforeKitkat(data,this);
                        if(t != null){
                            mList.add(t);
                        }
                    }
                }
                Toast.makeText(this,"长度："+mList.size(),Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
