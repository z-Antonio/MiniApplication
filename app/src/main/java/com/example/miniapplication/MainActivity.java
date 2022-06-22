package com.example.miniapplication;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.miniapplication.client.Callback;
import com.example.miniapplication.client.Schedule;
import com.example.miniapplication.service.DownloadListener;
import com.example.miniapplication.service.DownloadSchedule;
import com.example.miniapplication.utils.DisplayUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    public Resources getResources() {
        Resources resources = super.getResources();
        DisplayUtil.transformDisplay(resources.getDisplayMetrics());
        return resources;
    }

    private ListView mListView;
    private DownloadAdapter mAdapter;
    private List<Schedule> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = findViewById(R.id.id_list);
        mAdapter = new DownloadAdapter();
        mListView.setAdapter(mAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public class DownloadAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Schedule getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = (ViewHolder) convertView.getTag();
            if (holder == null) {
                convertView = ViewHolder.build(MainActivity.this);
                holder = (ViewHolder) convertView.getTag();
            }
            holder.bind(getItem(position));
            return convertView;
        }
    }

    public static class ViewHolder implements View.OnClickListener, Callback {
        public Context context;
        public ImageView image;
        public TextView name;
        public TextView status;
        public ProgressBar progress;
        public Button button;
        private Schedule schedule;

        public static View build(Context context) {
            View view = LayoutInflater.from(context).inflate(R.layout.list_item, null);
            ViewHolder holder = new ViewHolder();
            holder.context = context;
            holder.image = view.findViewById(R.id.id_item_image);
            holder.name = view.findViewById(R.id.id_item_name);
            holder.status = view.findViewById(R.id.id_item_status);
            holder.progress = view.findViewById(R.id.id_item_progress);
            holder.button = view.findViewById(R.id.id_item_btn);
            view.setTag(holder);
            return view;
        }

        public void bind(Schedule schedule) {
            this.schedule = schedule;
            schedule.setCallback(this);
            Glide.with(context).load(schedule.image).into(image);
            name.setText(schedule.name);
            button.setOnClickListener(this);
            update();
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onClick(View v) {
            if (schedule.status == Schedule.STATUS_SUCCESS) {
                return;
            }
            if (schedule.status == Schedule.STATUS_WAITING || schedule.status == Schedule.STATUS_DOWNLOAD) {
                DownloadSchedule.getInstance().cancel(schedule.url);
            } else {
                Schedule.Builder.build(schedule).start();
            }
        }

        @Override
        public void update() {
            switch (schedule.status) {
                case Schedule.STATUS_UNDOWNLOAD:
                    status.setVisibility(View.GONE);
                    progress.setVisibility(View.GONE);
                    button.setVisibility(View.VISIBLE);
                    button.setText("Download");
                    break;
                case Schedule.STATUS_DOWNLOAD:
                    status.setVisibility(View.GONE);
                    progress.setVisibility(View.VISIBLE);
                    // TODO
                    button.setVisibility(View.VISIBLE);
                    button.setText("Cancel");
                    break;
                case Schedule.STATUS_WAITING:
                    status.setVisibility(View.VISIBLE);
                    status.setText("waiting...");
                    progress.setVisibility(View.GONE);
                    button.setVisibility(View.VISIBLE);
                    button.setText("Cancel");
                    break;
                case Schedule.STATUS_SUCCESS:
                    status.setVisibility(View.VISIBLE);
                    status.setText("successfully");
                    progress.setVisibility(View.GONE);
                    button.setVisibility(View.GONE);
                    break;
                case Schedule.STATUS_FAILED:
                    status.setVisibility(View.VISIBLE);
                    status.setText("failed");
                    progress.setVisibility(View.GONE);
                    button.setVisibility(View.VISIBLE);
                    button.setText("Retry");
                    break;
            }
        }
    }
}