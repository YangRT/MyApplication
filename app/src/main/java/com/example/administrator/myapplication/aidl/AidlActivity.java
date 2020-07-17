package com.example.administrator.myapplication.aidl;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.administrator.myapplication.R;

public class AidlActivity extends AppCompatActivity {

    private IBookManager manager;

    private IBinder.DeathRecipient deathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            if(manager == null) return;
            manager.asBinder().unlinkToDeath(deathRecipient,0);
            manager = null;
            // 重新绑定
        }
    };



    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            manager = IBookManager.Stub.asInterface(service);
            try {
                service.linkToDeath(deathRecipient,0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl);
        Intent intent = new Intent(this,ManagerService.class);
    }
}
