package com.doanhung.sqlite.data.local_database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLHelper extends SQLiteOpenHelper {

    public static final String TO_DO_TABLE_NAME = "ToDo";
    public static final String ID_COLUMN = "ID", TO_DO_COLUMN = "ToDo", PLACE_COLUMN = "Place";
    private static final String CREATE_TO_DO_TB =
            "CREATE TABLE " + TO_DO_TABLE_NAME + "(" + ID_COLUMN + " INTEGER PRIMARY KEY," + TO_DO_COLUMN + " TEXT NOT NULL, " + PLACE_COLUMN  + " TEXT NOT NULL" + ")";

    public SQLHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TO_DO_TB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //noinspection SwitchStatementWithTooFewBranches
//        switch (oldVersion) {
//            case 1: db.execSQL("ALTER TABLE " + TO_DO_TABLE_NAME + " ADD COLUMN " + PLACE_COLUMN + " TEXT");
//            break;
//        }
    }
}
