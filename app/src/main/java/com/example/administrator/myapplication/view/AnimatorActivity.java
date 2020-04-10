package com.example.administrator.myapplication.view;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Spinner;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.classDemo.customview.ExplosionFiled;
import com.example.administrator.myapplication.classDemo.customview.FailingParticleFactory;
import com.example.administrator.myapplication.classDemo.loadingview.LoadingView;
import com.example.administrator.myapplication.view.slipToDelete.GridAdapter;
import com.example.administrator.myapplication.view.slipToDelete.ItemTouchCallback;

import java.util.ArrayList;
import java.util.List;

public class AnimatorActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private GridAdapter adapter;
    private List<String> data = new ArrayList<>();
    private ItemTouchCallback<String> callback;
    private ItemTouchHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animator);
        recyclerView = findViewById(R.id.animator_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this,4));
        initData();
        adapter = new GridAdapter(data,this);
        recyclerView.setAdapter(adapter);
        callback = new ItemTouchCallback<>(adapter,data);
        helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerView);

    }

    private void initData(){
        for (int i = 0;i < 50;i++){
            data.add(""+i);
        }
    }

    public void start(View view){
        ((LoadingView)view).startAllAnimator();
    }



}
