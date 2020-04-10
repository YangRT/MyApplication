package com.example.administrator.myapplication.view.layout_manager;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.example.administrator.myapplication.R;


import java.util.ArrayList;
import java.util.List;

public class LayoutMangerActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Bean> mList = new ArrayList<>();
    private ScaleRecyclerViewAdapter adapter;
    private android.support.v7.widget.RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_manger);
        recyclerView = findViewById(R.id.layout_manager_recycler_view);
        initData();
        layoutManager = new ScaleLayoutManger(1.5f, 0.85f);
        adapter = new ScaleRecyclerViewAdapter(mList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void initData(){
        for (int i = 0; i < 50; i++) {
            mList.add(new Bean("content = " + i, Color.parseColor(ColorUtils.generateRandomColor())));
        }
    }
}
