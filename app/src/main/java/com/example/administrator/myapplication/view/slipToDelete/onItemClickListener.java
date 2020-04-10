package com.example.administrator.myapplication.view.slipToDelete;

import android.view.View;

public interface onItemClickListener {

    void onClick(View view,int position);

    void onDeleteClick(int position);
}
