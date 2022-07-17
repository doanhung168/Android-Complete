package com.doanhung.sqlite.content_provider;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.doanhung.sqlite.data.local_database.DaoImp;

public class ToDoProvider extends ContentProvider {

    public static final String AUTHORITY = "com.doanhung.sqlite.provider";

    public static final String PATH_TODO_LIST = "list";
    public static final String PATH_TODO_PLACE = "place";
    public static final String PATH_TODO_COUNT = "count";

    public static final Uri CONTENT_URI_1 = Uri.parse("content://" + AUTHORITY + "/" + PATH_TODO_LIST);
    public static final Uri CONTENT_URI_2 = Uri.parse("content://" + AUTHORITY + "/" + PATH_TODO_PLACE);
    public static final Uri CONTENT_URI_3 = Uri.parse("content://" + AUTHORITY + "/" + PATH_TODO_COUNT);

    public static final int TODO_LIST = 1;
    public static final int TODO_PLACE = 2;
    public static final int TODO_COUNT = 3;

    private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        MATCHER.addURI(AUTHORITY, PATH_TODO_LIST, TODO_LIST);
        MATCHER.addURI(AUTHORITY, PATH_TODO_PLACE, TODO_PLACE);
        MATCHER.addURI(AUTHORITY, PATH_TODO_COUNT, TODO_COUNT);
    }

    public static final String CONTENT_TYPE_1 = ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.com.doanhung.sqlite.todo_list";
    public static final String CONTENT_TYPE_2 = ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.com.doanhung.sqlite.todo_place";
    public static final String CONTENT_TYPE_3 = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.com.doanhung.sqlite.todo_count";

    private DaoImp mDAO;


    @Override
    public boolean onCreate() {
        Toast.makeText(getContext(), "haha", Toast.LENGTH_SHORT).show();
        mDAO = DaoImp.getInstance(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        switch (MATCHER.match(uri)) {
            case TODO_LIST:
                Cursor cursor = mDAO.getCursorForAllToDo();
                cursor.setNotificationUri(getContext().getContentResolver(), Uri.parse("content://com.doanhung.sqlite.provider/*"));
                return cursor;
            case TODO_PLACE:
                return mDAO.getCursorForSpecificPlace(selectionArgs[0]);
            case TODO_COUNT:
                return mDAO.getCount();
        }
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (MATCHER.match(uri)) {
            case TODO_LIST:
                return CONTENT_TYPE_1;
            case TODO_PLACE:
                return CONTENT_TYPE_2;
            case TODO_COUNT:
                return CONTENT_TYPE_3;
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) throws UnsupportedOperationException {
        //noinspection SwitchStatementWithTooFewBranches
        switch (MATCHER.match(uri)) {
            case TODO_LIST:
                return insertTodo(uri, values);
            default:
                throw new UnsupportedOperationException("insert operation not supported");
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) throws UnsupportedOperationException {
        //noinspection SwitchStatementWithTooFewBranches
        switch (MATCHER.match(uri)) {
            case TODO_LIST: return delete(selection, selectionArgs);
            default: throw new UnsupportedOperationException("delete operation not supported");
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) throws UnsupportedOperationException {
        //noinspection SwitchStatementWithTooFewBranches
        switch (MATCHER.match(uri)) {
            case TODO_LIST:
                return update(values, selection, selectionArgs);
            default:
                throw new UnsupportedOperationException("update operation not supported");
        }
    }

    private Uri insertTodo(Uri uri, ContentValues contentValues) {
        long id = mDAO.insert(contentValues);
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse("content://" + AUTHORITY + "/" + PATH_TODO_LIST + "/" + id);
    }

    private int update(ContentValues values, String selection, String[] selectionArgs) {
        return mDAO.update(values, selection, selectionArgs);
    }

    private int delete(String selection, String[] selectionArgs) {
        return mDAO.delete(selection, selectionArgs);
    }
}
