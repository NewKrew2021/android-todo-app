package com.survivalcoding.todolist.model.db.asynctask

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.os.AsyncTask
import com.survivalcoding.todolist.model.TodoItem
import com.survivalcoding.todolist.model.db.TodoContract

class AddTodoAsyncTask(private val db: SQLiteDatabase) : AsyncTask<TodoItem, Unit, Unit>() {
    override fun doInBackground(vararg params: TodoItem) {
        val todoItem = params[0]
        val newItem = ContentValues().apply {
            put(TodoContract.TodoEntry.COLUMN_NAME_TITLE, todoItem.todoTitle)
            put(TodoContract.TodoEntry.COLUMN_NAME_IS_CHECKED, todoItem.isChecked)
            put(TodoContract.TodoEntry.COLUMN_NAME_TIME_STAMP, todoItem.timeStamp)
        }
        db.insert(TodoContract.TodoEntry.TABLE_NAME, null, newItem)
    }
}