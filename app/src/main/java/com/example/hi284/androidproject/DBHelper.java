package com.example.hi284.androidproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jkm21 on 2017-11-14.
 */

public class DBHelper extends SQLiteOpenHelper {
    final static String TAG = "DataBase";
    final int SUCCESS = 1;
    final int FAILS = -1;

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
        onCreate(sqLiteDatabase);
    }

    // Rests 테이블에 있는 모든 정보 가져오기
    public Cursor getAllRest() {
        SQLiteDatabase db = getReadableDatabase();
        return db.query(ResContract.Rests.REST_TABLE_NAME,null,null,null,null,null,null);
    }

    // 조건에 합하는 식당이 있는지 검사
    public Cursor getRest(String resName, String resNum) {
        SQLiteDatabase db = getReadableDatabase();
        String sql = String.format(
                "SELECT * FROM %s WHERE %s = '%s' AND %s = '%s'",
                ResContract.Rests.REST_TABLE_NAME,
                ResContract.Rests.REST_NAME,
                resName,
                ResContract.Rests.REST_NUM,
                resNum
                );
        try {
            Cursor c = getReadableDatabase().rawQuery(sql, null);
            return c;
        } catch (SQLiteException e) {
            return null;
        }
    }

    // Rests 테이블에 식당 추가 (식당이름, 식당번호, 식당사진에 대한 URI를 문자열로 받음)
    public int insertRest(String resName, String resNum, String resPic) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        if(getRest(resName, resNum) != null) {
            values.put(ResContract.Rests.REST_NAME, resName);
            values.put(ResContract.Rests.REST_NUM, resNum);
            values.put(ResContract.Rests.REST_PIC, resPic);

            db.insert(ResContract.Rests.REST_TABLE_NAME, null, values);
            if(createRestTable(resName) == SUCCESS) {
                return SUCCESS;
            } else
                return FAILS;
        } else {
            return FAILS;
        }
    }

    // 추가한 식당에 대한 메뉴테이블 만들기(식당이름을 문자열로 받음)
    public int createRestTable(String resName) {
        String TEXT_TYPE = " TEXT";
        String COMMA_SEP = ",";
        String TABLE_NAME = resName;
        String MENU = "Rest_Menu";
        String MENU_PRICE = "Rest_Price";
        String MENU_PIC = "Menu_Pic";

        SQLiteDatabase db = getWritableDatabase();

        String sql = "CREATE TABLE " + TABLE_NAME + " (" +
                        MENU + TEXT_TYPE + COMMA_SEP +
                        MENU_PRICE + TEXT_TYPE + COMMA_SEP +
                        MENU_PIC + TEXT_TYPE + " )";
        try {
            db.execSQL(sql);
            return SUCCESS;
        } catch (SQLiteException e) {
            return FAILS;
        }
    }

    // 메뉴 추가(식당이름, 메뉴이름, 메뉴가격, 메뉴사진에 대한 URI를 문자열로 받음)
    public void insertMenu(String resName, String menuName, String menuPrice, String menuPic) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Rest_Menu", menuName);
        values.put("Rest_Price", menuPrice);
        values.put("Rest_Pic", menuPic);

        db.insert(resName, null, values);
    }

    /*
    // Rests 테이블에 식당 삭제 (식당이름, 식당번호를 문자열로 받음)
    public long deleteRest(String resName, String resNum) {
        SQLiteDatabase db = getWritableDatabase();

        String whereClause = ResContract.Rests.REST_NAME +" = ? AND " + ResContract.Rests.REST_NUM+" = ?";
        String[] whereArgs ={resName, resNum};
        return db.delete(ResContract.Rests.REST_TABLE_NAME, whereClause, whereArgs);
    }
    */
}
