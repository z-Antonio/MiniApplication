package com.example.miniapplication.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DBHelper extends OrmLiteSqliteOpenHelper {

    private static final String DB_NAME = "mini";
    private static final int DB_VERSION = 1;

    public static class Build {
        private static DBHelper instance;

        public static void build(Context context, Class... cls) {
            instance = new DBHelper(context, cls);
        }
    }

    public static DBHelper getInstance() {
        if (Build.instance == null) {
            throw new RuntimeException("DBHelper.Build.build() method must be called");
        }
        return Build.instance;
    }

    private Class[] cls;

    private DBHelper(Context context, Class... cls) {
        super(context, DB_NAME, null, DB_VERSION);
        this.cls = cls;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            for (Class clz : cls) {
                TableUtils.createTableIfNotExists(connectionSource, clz);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
    }

}
