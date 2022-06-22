package com.example.miniapplication;

import android.app.Application;

import com.example.miniapplication.database.DBHelper;
import com.example.miniapplication.database.Model;

public class MiniApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
//        DBHelper.Build.build(this, Model.class);
    }

}
