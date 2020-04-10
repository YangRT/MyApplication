package com.example.administrator.myapplication.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ManagerService extends Service {

    private static final String TAG = "ManagerService";

    private CopyOnWriteArrayList<Book> mList = new CopyOnWriteArrayList<>();

    private Binder mBinder = new IBookManager.Stub() {
        @Override
        public List<Book> getBookList() throws RemoteException {
            return mList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            mList.add(book);
        }
    };


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
