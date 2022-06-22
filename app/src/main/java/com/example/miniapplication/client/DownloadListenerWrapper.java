package com.example.miniapplication.client;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import com.example.miniapplication.service.DownloadListener;

import java.lang.ref.WeakReference;

public class DownloadListenerWrapper implements DownloadListener {

    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            DownloadListener listener = listenerRef.get();
            if (listener != null) {
                switch (msg.what) {
                    case 0:
                        listener.onProgressUpdate((Integer) msg.obj);
                        break;
                    case 1:
                        listener.onSuccess((String) msg.obj);
                        break;
                    case 2:
                        listener.onFailure((String) msg.obj);
                        break;
                }
            }
        }
    };

    private WeakReference<DownloadListener> listenerRef;

    public DownloadListenerWrapper(DownloadListener listener) {
        this.listenerRef = new WeakReference<>(listener);
    }

    @Override
    public void onProgressUpdate(int progress) {
        handler.obtainMessage(0, progress);
    }

    @Override
    public void onSuccess(String file) {
        handler.obtainMessage(1, file);
    }

    @Override
    public void onFailure(String reason) {
        handler.obtainMessage(2, reason);
    }
}
