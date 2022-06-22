package com.example.miniapplication.client;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import com.example.miniapplication.service.IProdInterface;
import com.example.miniapplication.service.Msg;
import com.example.miniapplication.service.Result;

public class ConsClient implements IProdInterface {

    private Context context;
    private IProdInterface produce;
    private boolean connected = false;

    public ConsClient(Context context) {
        this.context = context;
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            produce = IProdInterface.Stub.asInterface(service);
            connected = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            produce = null;
            connected = false;
        }
    };

    public void bindService() {
        Intent intent = new Intent();
        intent.setPackage("com.example.miniapplication");
        intent.setAction("com.example.miniapplication.service.ACTION");
        context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    public void unbindService() {
        if (connected) {
            context.unbindService(serviceConnection);
        }
    }

    @Override
    public Result action(Msg msg) throws RemoteException {
        return produce != null ? produce.action(msg) : null;
    }

    @Deprecated
    @Override
    public IBinder asBinder() {
        return null;
    }
}
