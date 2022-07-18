package com.doanhung.sharing_files;

import static com.doanhung.sharing_files.MainActivity.KEY;
import static com.doanhung.sharing_files.MainActivity.SHARED_FILE_NAME;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class SharedPreferencesContentProvider extends ContentProvider {

    public static final String AUTHORITY = "com.doanhung.sharing_files.provider";
    public static final int MATCH_DATA = 1;
    public static UriMatcher sUriMather;

    private SharedPreferences mSharedPreferences;

    static {
        sUriMather = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMather.addURI(AUTHORITY, "/*", MATCH_DATA);
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        //noinspection SwitchStatementWithTooFewBranches
        switch (sUriMather.match(uri)) {
            case MATCH_DATA:
                String SECRET = uri.getPathSegments().get(0);
                try {
                    MasterKey masterKey =
                            new MasterKey.Builder(getContext(), SECRET)
                                    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                                    .build();
                    mSharedPreferences = EncryptedSharedPreferences.create(
                            getContext(),
                            SHARED_FILE_NAME,
                            masterKey,
                             EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
                    );
                } catch (GeneralSecurityException | IOException e) {
                    e.printStackTrace();
                }
                MatrixCursor matrixCursor = new MatrixCursor(new String[]{"f0"});
                if (mSharedPreferences != null) {
                    String name = mSharedPreferences.getString(KEY, "");
                    MatrixCursor.RowBuilder rowBuilder = matrixCursor.newRow();
                    rowBuilder.add(name);
                }
                return matrixCursor;
            default:
                throw new UnsupportedOperationException("Query not supported !!");
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd." + AUTHORITY + ".item";
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
