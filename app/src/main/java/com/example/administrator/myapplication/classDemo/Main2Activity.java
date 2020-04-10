package com.example.administrator.myapplication.classDemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.classDemo.eventBus.EventBean;
import com.example.administrator.myapplication.classDemo.eventBus.EventBus;
import com.example.administrator.myapplication.classDemo.eventBus.Subscribe;
import com.example.administrator.myapplication.classDemo.eventBus.ThreadMode;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        EventBus.getDefault().register(this);
        Button button = findViewById(R.id.bt_tiao);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main2Activity.this,Main3Activity.class);
                startActivity(intent);

            }
        });
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void getEvent(EventBean bean){
        Log.d("===>","thread=="+Thread.currentThread().getName());
        Log.d("===>",bean.toString());
    }
}
