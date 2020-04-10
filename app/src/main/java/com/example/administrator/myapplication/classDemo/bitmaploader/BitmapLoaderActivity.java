package com.example.administrator.myapplication.classDemo.bitmaploader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import com.example.administrator.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class BitmapLoaderActivity extends AppCompatActivity {
    List<String> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap_loader);
        GridView gridView = findViewById(R.id.my_gridview);
        init();
        BitmapAdapter adapter = new BitmapAdapter(this,list);
        gridView.setAdapter(adapter);
    }

    private void init() {
        list.add("https://www.wanandroid.com/blogimgs/90c6cc12-742e-4c9f-b318-b912f163b8d0.png");
        list.add("https://wanandroid.com/blogimgs/65449f57-9335-47ef-921f-44fca0b87c90.jpg");
        list.add("https://www.wanandroid.com/blogimgs/62c1bd68-b5f3-4a3c-a649-7ca8c7dfabe6.png");
        list.add("http://picture.ik123.com/uploads/allimg/151020/3-151020142125.jpg");
        list.add("https://p0.ssl.qhimgs1.com/sdr/400__/t01255e12f79dc8e77a.jpg");
    }
}
