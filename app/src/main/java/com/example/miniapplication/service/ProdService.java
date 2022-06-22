package com.example.miniapplication.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;

public class ProdService extends Service {

    private static final String TAG = ProdService.class.getSimpleName();

    private IProdInterface.Stub stub = new IProdInterface.Stub() {


        @Override
        public Result action(Msg msg) throws RemoteException {
            Log.i(TAG, "obtain message : " + msg.getMessage());
            return new Result(msg.getMessage() + ": ok!");
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "service onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "service onDestroy");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "service onBind : " + intent.getAction());
        return stub;
    }
}
