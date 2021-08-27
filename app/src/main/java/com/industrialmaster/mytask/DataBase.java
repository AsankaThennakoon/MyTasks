package com.industrialmaster.mytask;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBase extends SQLiteOpenHelper {


    Context context;

    public DataBase(Context context) {

        super(context, "db_my_tasks", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE person(_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT,address TEXT,contact_number INTEGER,email TEXT,password TEXT,profile_image_path TEXT)";
        db.execSQL(sql);


        String sql2 = "CREATE TABLE tasks(_id INTEGER PRIMARY KEY AUTOINCREMENT,title TEXT,date TEXT,time TEXT,description TEXT)";
        db.execSQL(sql2);

        //tasks(title,date,time,description)
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
