package com.bawei.yunifang.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Pooh on 2016/12/9.
 */
public class MySqlite extends SQLiteOpenHelper{
    public MySqlite(Context context) {
        super(context, "com.bwie.sql", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table shopping (name varchar(200),url varchar(200),price integer,number integer,tag integer)");
        db.execSQL("create table address (name varchar(200),phone varchar(100),address varchar(100),detailaddress varchar(500),tag varchar(50))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
