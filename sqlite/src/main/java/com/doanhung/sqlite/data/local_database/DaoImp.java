package com.doanhung.sqlite.data.local_database;

import static com.doanhung.sqlite.data.local_database.SQLHelper.ID_COLUMN;
import static com.doanhung.sqlite.data.local_database.SQLHelper.PLACE_COLUMN;
import static com.doanhung.sqlite.data.local_database.SQLHelper.TO_DO_COLUMN;
import static com.doanhung.sqlite.data.local_database.SQLHelper.TO_DO_TABLE_NAME;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.doanhung.sqlite.data.model.Todo;

import java.util.ArrayList;
import java.util.List;

public class DaoImp implements LocalDAO, RemoteDAO {

    private final SQLiteDatabase mDB;
    private final Context mContext;

    private DaoImp(Context context) {
        mContext = context;
        mDB = new SQLHelper(context, "myDB", null, 2).getWritableDatabase();
    }

    public SQLiteDatabase getDB() {
        return mDB;
    }

    private static DaoImp sInstance;

    public static DaoImp getInstance(Context context) {
        synchronized (DaoImp.class) {
            if (sInstance == null) {
                sInstance = new DaoImp(context);
            }
        }
        return sInstance;
    }


    @Override
    public List<Todo> getAllToDo() {
        List<Todo> todoList = new ArrayList<>();
        Cursor cursor = mDB.query(
                TO_DO_TABLE_NAME,
                new String[]{ID_COLUMN, TO_DO_COLUMN},
                null,
                null,
                null,
                null,
                null
        );

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                Todo todo = new Todo(cursor.getLong(0), cursor.getString(1));
                todoList.add(todo);
            }
            cursor.close();
        }
        return todoList;
    }

    @Override
    public boolean insert(String toDoItem, String place) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TO_DO_COLUMN, toDoItem);
        contentValues.put(PLACE_COLUMN, place);
        boolean result = mDB.insert(TO_DO_TABLE_NAME, null, contentValues) > 0;
        mContext.getContentResolver().notifyChange(Uri.parse("content://com.doanhung.sqlite.provider/*"), null);
        return result;
    }


    @Override
    public boolean update(int id, String toDoItem) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TO_DO_COLUMN, toDoItem);
        return mDB.update(
                TO_DO_TABLE_NAME,
                contentValues,
                ID_COLUMN + " = ?",
                new String[]{String.valueOf(id)}
        ) > 0;
    }

    @Override
    public boolean delete(int id) {
        return mDB.delete(
                TO_DO_TABLE_NAME,
                ID_COLUMN + " = ?",
                new String[]{String.valueOf(id)}
        ) > 0;
    }

    @Override
    public Cursor getCursorForAllToDo() {
        return mDB.query(TO_DO_TABLE_NAME, new String[]{ID_COLUMN, TO_DO_COLUMN, PLACE_COLUMN}, null, null, null, null, null);
    }

    @Override
    public Cursor getCursorForSpecificPlace(String place) {
        return mDB.query(
                TO_DO_TABLE_NAME,
                new String[]{ID_COLUMN, TO_DO_COLUMN, PLACE_COLUMN},
                PLACE_COLUMN + " LIKE ?",
                new String[]{place},
                null,
                null,
                null
        );
    }

    @Override
    public Cursor getCount() {
        return mDB.rawQuery("SELECT COUNT(*) FROM " + TO_DO_TABLE_NAME, null);
    }

    @Override
    public long insert(ContentValues contentValues) {
        return mDB.insert(TO_DO_TABLE_NAME, null, contentValues);
    }

    @Override
    public int update(ContentValues contentValues, String whereClause, String[] args) {
        return mDB.update(TO_DO_TABLE_NAME, contentValues, whereClause, args);
    }

    @Override
    public int delete(String whereClause, String[] args) {
        return mDB.delete(TO_DO_TABLE_NAME, whereClause, args);
    }

}
