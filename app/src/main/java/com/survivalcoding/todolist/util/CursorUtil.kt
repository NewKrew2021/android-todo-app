package com.survivalcoding.todolist.util

import android.database.Cursor
import com.survivalcoding.todolist.model.TodoItem

// cursor.use.getTodoItem() 형태로 사용해야함
fun Cursor.getTodoItem(): List<TodoItem> {
    val todoItems = mutableListOf<TodoItem>()
    with(this) {
        while (moveToNext()) {
            val id = getInt(getColumnIndex(android.provider.BaseColumns._ID))
            val isChecked =
                getInt(getColumnIndex(com.survivalcoding.todolist.model.db.TodoContract.TodoEntry.COLUMN_NAME_IS_CHECKED))
            val todoTitle =
                getString(getColumnIndex(com.survivalcoding.todolist.model.db.TodoContract.TodoEntry.COLUMN_NAME_TITLE))
            val timeStamp =
                getString(getColumnIndex(com.survivalcoding.todolist.model.db.TodoContract.TodoEntry.COLUMN_NAME_TIME_STAMP))

            todoItems.add(TodoItem(id, isChecked == 1, todoTitle, timeStamp))
        }
    }
    return todoItems.toList()
}