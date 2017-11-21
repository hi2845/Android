package com.example.hi284.androidproject;

import android.provider.BaseColumns;

/**
 * Created by jkm21 on 2017-11-14.
 */

public final class ResContract {
    public static final String DB_NAME = "res.db";
    public static final int DATABASE_VERSION = 1;
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    private ResContract(){}

    public static class Rests implements BaseColumns {
        public static final String REST_TABLE_NAME = "Rests";
        public static final String REST_NAME = "Rest_Name";
        public static final String REST_NUM = "Rest_Num";
        public static final String REST_ADDR = "Rest_Address";
        public static final String REST_PIC = "Rest_Pic";

        public static final String CREATE_TABLE = "CREATE TABLE " + REST_TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY" + COMMA_SEP +
                REST_NAME + TEXT_TYPE + COMMA_SEP +
                REST_NUM + TEXT_TYPE + COMMA_SEP +
                REST_ADDR + TEXT_TYPE + COMMA_SEP +
                REST_PIC + TEXT_TYPE + " )";
        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + REST_TABLE_NAME;
    }
}
