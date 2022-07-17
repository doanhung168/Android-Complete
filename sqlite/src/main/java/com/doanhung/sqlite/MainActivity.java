package com.doanhung.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.doanhung.sqlite.data.local_database.LocalDAO;
import com.doanhung.sqlite.data.local_database.DaoImp;
import com.doanhung.sqlite.data.model.Todo;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText edtID;
    private Button btnInsert;
    private EditText edtContent;
    private EditText edtPlace;
    private Button btnUpdate;
    private Button btnDelete;
    private Button btnGetAll;

    private LocalDAO mLocalDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        mLocalDao = DaoImp.getInstance(getApplicationContext());

        btnGetAll.setOnClickListener(v -> {
            List<Todo> todoList = mLocalDao.getAllToDo();
            StringBuilder stringBuilder = new StringBuilder();
            if(todoList.size() > 0) {
                for(Todo todo: todoList) {
                    stringBuilder.append(todo.getContent()).append(" ");
                }
                Toast.makeText(getApplicationContext(), stringBuilder, Toast.LENGTH_SHORT).show();
            }
        });

        btnInsert.setOnClickListener(v -> {
            String content = edtContent.getText().toString().trim();
            String place = edtPlace.getText().toString().trim();
            mLocalDao.insert(content, place);
        });

        btnUpdate.setOnClickListener(v -> {
            int id = Integer.parseInt(edtID.getText().toString().trim());
            String content = edtContent.getText().toString().trim();
            mLocalDao.update(id, content);
        });

        btnDelete.setOnClickListener(v -> {
            int id = Integer.parseInt(edtID.getText().toString().trim());
            mLocalDao.delete(id);
        });
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
}