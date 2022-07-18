package com.doanhung.sharing_files;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class MainActivity extends AppCompatActivity {

    private EditText edtContent;
    private Button btnSave;
    private Button btnDisplay;

    public static final String SHARED_FILE_NAME = "FILE_NAME";
    public static final String KEY = "KEY";
    private SharedPreferences mSharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        try {
            MasterKey masterKey =
                    new MasterKey.Builder(getApplication(), Contract.SECRET)
                            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                            .build();
            mSharedPreferences = EncryptedSharedPreferences.create(
                    getApplicationContext(),
                    SHARED_FILE_NAME,
                    masterKey,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }


        btnSave.setOnClickListener(v -> {
            String data = edtContent.getText().toString().trim();
            saveData(data);
        });

        btnDisplay.setOnClickListener(v -> loadData());

    }

    private void saveData(String data) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(KEY, data);
        editor.apply();
    }

    private void loadData() {
        String data = mSharedPreferences.getString(KEY, "");
        Toast.makeText(getApplicationContext(), data, Toast.LENGTH_SHORT).show();
    }

    private void initView() {
        edtContent = findViewById(R.id.edtContent);
        btnSave = findViewById(R.id.btnSave);
        btnDisplay = findViewById(R.id.btnDisplay);
    }
}