package com.example.hi284.androidproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by jkm21 on 2017-11-14.
 */

public class DBHelper extends SQLiteOpenHelper {
    final static String TAG = "DataBase";
    final int SUCCESS = 1;
    final int FAILS = -1;
    private SQLiteDatabase db;

    public DBHelper(Context context) {
        super(context, ResContract.DB_NAME, null, ResContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.i(TAG,getClass().getName() +".onCreate()");
        sqLiteDatabase.execSQL(ResContract.Rests.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.i(TAG,getClass().getName() +".onUpgrade()");
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
            Cursor c = db.rawQuery(sql, null);
            return c;
        } catch (SQLiteException e) {
            return null;
        }
    }

    // Rests 테이블에 식당 추가 (식당이름, 식당번호, 식당주소, 식당사진에 대한 URI를 문자열로 받음)
    public int insertRest(String resName, String resNum, String resAddr, String resPic) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        if(getRest(resName, resNum) != null) {
            values.put(ResContract.Rests.REST_NAME, resName);
            values.put(ResContract.Rests.REST_NUM, resNum);
            values.put(ResContract.Rests.REST_ADDR, resAddr);
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
        if(getMenu(resName, menuName) != null) {
            values.put("Rest_Menu", menuName);
            values.put("Rest_Price", menuPrice);
            values.put("Rest_Pic", menuPic);

            db.insert(resName, null, values);
        }
    }

    // 조건에 합하는 메뉴가 있는지 검사
    public Cursor getMenu(String resName, String menuName) {
        SQLiteDatabase db = getReadableDatabase();
        String sql = String.format(
                "SELECT * FROM %s WHERE %s = '%s'",
                resName,
                "Rest_Menu",
                menuName
        );
        try {
            Cursor c = db.rawQuery(sql, null);
            return c;
        } catch (SQLiteException e) {
            return null;
        }
    }


    // Rests 테이블에 식당 삭제 (식당이름, 식당번호를 문자열로 받음)
    public long deleteRest(String resName, String resNum) {
        SQLiteDatabase db = getWritableDatabase();

        try {
            String sql = String.format (
                    "DELETE FROM %s WHERE %s = '%s' AND %s = '%s'",
                    ResContract.Rests.REST_TABLE_NAME,
                    ResContract.Rests.REST_NAME,
                    resName,
                    ResContract.Rests.REST_NUM,
                    resNum);
            db.execSQL(sql);
            return 1;
        } catch (SQLException e) {
            Log.e(TAG,"Error in deleting recodes");
            return -1;
        }
    }

    public long updateUserByMethod(String _id, String name, String phone) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ResContract.Rests.REST_NAME, name);
        values.put(ResContract.Rests.REST_NUM,phone);

        String whereClause = ResContract.Rests._ID +" = ?";
        String[] whereArgs ={_id};

        return db.update(ResContract.Rests.REST_TABLE_NAME, values, whereClause, whereArgs);
    }
    public Cursor fetchBook(long rowID) throws SQLException {
        Cursor Cursor =
                db.query(true, ResContract.Rests.CREATE_TABLE, new String[]{
                        ResContract.Rests.REST_NAME,ResContract.Rests.REST_NUM,ResContract.Rests.REST_ADDR},
                        ResContract.Rests._ID + "=" + rowID, null, null, null, null, null);

        if(Cursor != null)
            Cursor.moveToFirst();

        return Cursor;

    }
    public RestClass getPersonById(int _id)
    { StringBuffer sb = new StringBuffer();
    sb.append(" SELECT NAME, AGE, PHONE, ADDRESS FROM TEST_TABLE WHERE _ID = ? ");
    SQLiteDatabase db = getReadableDatabase();
    Cursor cursor = db.rawQuery(sb.toString(), new String[]{_id + ""});
    RestClass restClass= new RestClass();
    if(cursor.moveToNext())
    {
        restClass.setName(cursor.getString(0));
        restClass.setPhone(cursor.getString(1));
        restClass.setAddr(cursor.getString(2));
    }
    return restClass; }





}
