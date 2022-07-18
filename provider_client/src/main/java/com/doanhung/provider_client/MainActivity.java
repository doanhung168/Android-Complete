package com.doanhung.provider_client;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int LOADER_ID = 1;
    private EditText edtID;
    private Button btnInsert;
    private EditText edtContent;
    private Button btnUpdate;
    private Button btnDelete;
    private Button btnGetAll;
    private EditText edtPlace;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        LoaderManager.getInstance(this).initLoader(LOADER_ID, null, this);
    }

    private void initView() {
        edtID = findViewById(R.id.edtID);
        btnInsert = findViewById(R.id.btnInsert);
        edtContent = findViewById(R.id.edtContent);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnGetAll = findViewById(R.id.btnGetAll);
        edtPlace = findViewById(R.id.edtPlace);
    }

    @NonNull
    @Override
    public Loader onCreateLoader(int id, @Nullable Bundle args) {
        if(id == LOADER_ID) {
            Uri uri = new Uri.Builder()
                    .scheme("content")
                    .authority("com.doanhung.sharing_files.provider")
                    .appendPath(Contract.SECRET)
                    .build();

            return new CursorLoader(
                    this,
                    uri,
                    null, null, null, null
                    );
        }
        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToPosition(-1);
            StringBuilder stringBuilder = new StringBuilder();
            while (cursor.moveToNext()) {
                stringBuilder.append(cursor.getString(0)).append("\n");
            }
            Toast.makeText(getApplicationContext(), stringBuilder, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader loader) {

    }
}