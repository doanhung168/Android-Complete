package com.doanhung.sqlite.data.local_database;

import android.content.ContentValues;
import android.database.Cursor;

public interface RemoteDAO {
    Cursor getCursorForAllToDo();
    Cursor getCursorForSpecificPlace(String place);
    Cursor getCount();

    long insert(ContentValues contentValues);
    int update(ContentValues contentValues, String whereClause, String[] args);
    int delete(String whereClause, String[] args);

}
