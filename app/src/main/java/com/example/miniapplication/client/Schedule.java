package com.example.miniapplication.client;

import com.example.miniapplication.service.DownloadListener;
import com.example.miniapplication.service.DownloadSchedule;

public class Schedule implements DownloadListener {

    public static final int STATUS_UNDOWNLOAD = 0;
    public static final int STATUS_DOWNLOAD = 1;
    public static final int STATUS_WAITING = 2;
    public static final int STATUS_SUCCESS = 3;
    public static final int STATUS_FAILED = 4;

    public String url;
    public String image;
    public String name;
    public String file;
    public int status;
    public int progress;
    private Callback callback;

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    @Override
    public void onProgressUpdate(int progress) {
        this.progress = progress;
        if (callback != null) {
            callback.update();
        }
    }

    @Override
    public void onSuccess(String file) {
        status = STATUS_SUCCESS;
        this.file = file;
        if (callback != null) {
            callback.update();
        }
    }

    @Override
    public void onFailure(String reason) {
        status = STATUS_FAILED;
        if (callback != null) {
            callback.update();
        }
    }

    public static class Builder {

        private String url;
        private DownloadListenerWrapper listener;

        private Builder(String ur) {
            this.url = url;
        }

        public String getUrl() {
            return url;
        }

        public Builder setListener(DownloadListener listener) {
            this.listener = new DownloadListenerWrapper(listener);
            return this;
        }

        public DownloadListener getListener() {
            return listener;
        }

        public static Builder build(Schedule schedule) {
            Builder builder = new Builder(schedule.url);
            builder.setListener(schedule);
           return builder;
        }

        public void start() {
            DownloadSchedule.getInstance().executor(this);
        }
    }
}
