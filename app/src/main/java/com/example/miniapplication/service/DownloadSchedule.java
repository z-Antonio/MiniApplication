package com.example.miniapplication.service;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.miniapplication.client.Schedule;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

public class DownloadSchedule {

    static class Factory {
        private static final DownloadSchedule INSTANCE = new DownloadSchedule();
    }

    public static DownloadSchedule getInstance() {
        return Factory.INSTANCE;
    }

    private DownloadSchedule() {
    }

    private LinkedBlockingQueue queue = new LinkedBlockingQueue<Task>();

    private ExecutorService executorService = new ThreadPoolExecutor(1, 2,
            0L, TimeUnit.MILLISECONDS,
            queue);

    private IDownloadService downloadService = new IDownloadService();

    public void executor(Schedule.Builder builder) {
        executorService.execute(new Task(builder));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void cancel(String url) {
        queue.removeIf(new Predicate<Task>() {
            @Override
            public boolean test(Task task) {
                return task.url.equals(url);
            }
        });
        downloadService.cancel(url);
    }

    class Task implements Runnable {
        private String url;
        private DownloadListener listener;

        public Task(Schedule.Builder builder) {
            this.url = builder.getUrl();
            this.listener = builder.getListener();
        }

        @Override
        public void run() {
            downloadService.download(url, listener);
        }
    }
}
