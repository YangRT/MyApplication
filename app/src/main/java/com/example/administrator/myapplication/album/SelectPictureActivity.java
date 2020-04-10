package com.example.administrator.myapplication.album;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.administrator.myapplication.R;


import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SelectPictureActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "SelectPictureActivity";

    private Button btFinish;
    private ImageView ivClose;
    private GridView gvPictures;
    private View actionbar;
    private TextView tvImageType;
    private ImageView imageType;
    private LinearLayout layoutType;
    private Handler mHandler;
    private int lastChose = 0;
    private final Context context = this;
    private List<String> mList = new ArrayList<>();
    private List<PictureType> typeList = new ArrayList<>();
    private List<String> adapterList = new ArrayList<>();
    private Map<String,Integer> typeName = new HashMap<>();
    private List<PictureInformation> pictureInformationList = new ArrayList<>();
    private static GridViewAdapter mAdapter;
    private PopupWindow mWindow;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_picture);
        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        init();

    }

    private void init(){
        gvPictures = findViewById(R.id.gridview_select_picture);
        actionbar = findViewById(R.id.actionbar_select);
        btFinish = actionbar.findViewById(R.id.actionbar_finish);
        ivClose = actionbar.findViewById(R.id.actionbar_close);
        layoutType = actionbar.findViewById(R.id.actionbar_type_layout);
        tvImageType = actionbar.findViewById(R.id.actionbar_type_tv);
        imageType = actionbar.findViewById(R.id.actionbar_type_image);
        btFinish.setOnClickListener(this);
        ivClose.setOnClickListener(this);
        layoutType.setOnClickListener(this);
        final Context context = this;
        mHandler  = new MyHandler(this);

        new Thread(new Runnable() {
            @Override
            public void run() {
                mList = GetAlbumPicture.getAllImagePath(context);
                adapterList.addAll(mList);
                Message message = Message.obtain();
                message.what = 1;
                mHandler.sendMessage(message);
            }
        }).start();

        initPopupWindow();
    }

    private void initPopupWindow() {
        View view = LayoutInflater.from(this).inflate(R.layout.popup_window_picture_type,null,false);
        recyclerView = view.findViewById(R.id.recycler_picture_type);
        recyclerViewAdapter = new RecyclerViewAdapter(context,typeList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recyclerViewAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(this, R.drawable.recycler_line)));
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerViewAdapter.setListener(new RecyclerViewAdapter.ChoiseListener() {
            @Override
            public void choseChanged(int position) {
                if(mWindow.isShowing()){
                    mWindow.dismiss();
                    typeList.get(position).setChose(true);
                    typeList.get(lastChose).setChose(false);
                    lastChose = position;
                    recyclerView.setAdapter(recyclerViewAdapter);
                    if(position == 0){
                        adapterList.clear();
                        adapterList.addAll(mList);
                        mAdapter.notifyDataSetChanged();
                        return;
                    }
                    String type = "";
                    for(String key:typeName.keySet()){
                        if(typeName.get(key) == position){
                            type = key;
                            break;
                        }
                    }
                    adapterList.clear();
                    for(String s:mList){
                        if(s.contains(type)){
                            adapterList.add(s);
                        }
                    }
                    mAdapter.notifyDataSetChanged();
                    tvImageType.setText(typeList.get(position).getType());
                }
            }
        });
        mWindow = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        mWindow.setFocusable(true);
        mWindow.setTouchable(true);
        mWindow.setOutsideTouchable(true);
        mWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mWindow.setAnimationStyle(R.style.popupWindowAnim);
        mWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(imageType,"rotation",180f,360f);
                objectAnimator1.setDuration(200);
                objectAnimator1.start();
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.actionbar_finish:
                List<String> selectPic = mAdapter.getSelectedPic();
                break;
            case R.id.actionbar_close:
                finish();
                break;
            case R.id.actionbar_type_layout:
                    ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(imageType,"rotation",0f,180f);
                    objectAnimator.setDuration(200);
                    objectAnimator.start();
                    mWindow.showAsDropDown(actionbar);
                break;
        }
    }

    //解决内存泄漏
     static class MyHandler extends Handler{

        private final WeakReference<SelectPictureActivity> mActivity;

        MyHandler(SelectPictureActivity activity){
            mActivity  = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            boolean i =  Looper.getMainLooper().getThread() == Thread.currentThread();
            Log.e(TAG,i+";");
            if(msg.what == 1){
                mAdapter = new GridViewAdapter(mActivity.get(),mActivity.get().adapterList);
                mActivity.get().gvPictures.setAdapter(mAdapter);
                mAdapter.setSelectedPicListener(new GridViewAdapter.SelectPicListener() {
                    @Override
                    public void selectedNum(int num) {
                        mActivity.get().btFinish.setText("完成("+num+"/9)");
                    }
                });
                int index = 1;
                PictureType  pictureType = new PictureType();
                pictureType.setNum(mActivity.get().mList.size());
                pictureType.setType("所有图片");
                pictureType.setChose(true);
                pictureType.setPath(mActivity.get().mList.get(0));
                mActivity.get().typeList.add(pictureType);
                for(String type:mActivity.get().mList){
                    String[] temp = type.split("/");
                    Log.e(TAG,temp[temp.length-2]);
                    PictureInformation information = new PictureInformation();
                    information.setChose(false);
                    information.setPath(type);
                    information.setType(temp[temp.length-2]);
                    mActivity.get().pictureInformationList.add(information);
                    if(mActivity.get().typeName.containsKey(temp[temp.length-2])){
                        mActivity.get().typeList.get(mActivity.get().typeName.get(temp[temp.length-2])).addNum();
                    }else {
                        mActivity.get().typeName.put(temp[temp.length-2],index);
                        PictureType  pictureType1 = new PictureType();
                        pictureType1.setNum(1);
                        pictureType1.setType(temp[temp.length-2]);
                        pictureType1.setPath(type);
                        mActivity.get().typeList.add(pictureType1);
                        index++;
                    }
                }
            }
            super.handleMessage(msg);
        }
    }

}
