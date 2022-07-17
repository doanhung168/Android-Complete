package com.doanhung.sqlite.data.local_database;

import android.database.Cursor;

import com.doanhung.sqlite.data.model.Todo;

import java.util.List;

public interface LocalDAO {
    List<Todo> getAllToDo();
    boolean insert(String toDoItem, String place);
    boolean update(int id, String toDoItem);
    boolean delete(int id);
}
