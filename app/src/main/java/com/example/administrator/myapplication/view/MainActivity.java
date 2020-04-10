package com.example.administrator.myapplication.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.view.slipToDelete.MyRecyclerView;
import com.example.administrator.myapplication.view.slipToDelete.adapter;
import com.example.administrator.myapplication.view.slipToDelete.onItemClickListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//         MyRecyclerView recyclerView = findViewById(R.id.my_view);
//         recyclerView.setAdapter(new adapter(this));
//         recyclerView.setLayoutManager(new LinearLayoutManager(this));
//         recyclerView.setListener(new onItemClickListener() {
//             @Override
//             public void onClick(View view, int position) {
//                 Toast.makeText(MainActivity.this,"777",Toast.LENGTH_LONG).show();
//             }
//
//             @Override
//             public void onDeleteClick(int position) {
//                 Toast.makeText(MainActivity.this,"888",Toast.LENGTH_LONG).show();
//             }
//         });


    }

}
