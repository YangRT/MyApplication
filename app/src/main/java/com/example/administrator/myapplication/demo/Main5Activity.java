package com.example.administrator.myapplication.demo;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class Main5Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        my viewPager = findViewById(R.id.my_viewpager);
        final List<View> list = new ArrayList<>();
        LayoutInflater lf = getLayoutInflater().from(this);
        View view = lf.inflate(R.layout.viewpager_item,null);
        View view1 = lf.inflate(R.layout.viewpager_item,null);
        View view2 = lf.inflate(R.layout.viewpager_item,null);
        list.add(view);
        list.add(view1);
        list.add(view2);
        PagerAdapter adapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return list.size();
            }


            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
                return view == o;
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                container.addView(list.get(position));
                return list.get(position);
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView(list.get(position));
            }
        };
        viewPager.setAdapter(adapter);
    }
}
