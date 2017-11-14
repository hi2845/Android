package com.example.hi284.androidproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jkm21 on 2017-11-14.
 */

public class DBHelper extends SQLiteOpenHelper {
    final static String TAG = "DataBase";

    public DBHelper(Context context) {
        super(context, ResContract.DB_NAME, null, ResContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(ResContract.Rests.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(ResContract.Rests.DELETE_TABLE);
    }
}
