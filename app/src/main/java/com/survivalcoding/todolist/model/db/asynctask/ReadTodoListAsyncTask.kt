package com.survivalcoding.todolist.model.db.asynctask

import android.database.sqlite.SQLiteDatabase
import android.os.AsyncTask
import android.provider.BaseColumns
import com.survivalcoding.todolist.model.TodoItem
import com.survivalcoding.todolist.model.db.TodoContract
import com.survivalcoding.todolist.util.getTodoItem

class ReadTodoListAsyncTask(private val db: SQLiteDatabase) :
    AsyncTask<Unit, Unit, List<TodoItem>>() {
    override fun doInBackground(vararg params: Unit): List<TodoItem> {
        val projection = arrayOf(
            BaseColumns._ID,
            TodoContract.TodoEntry.COLUMN_NAME_TITLE,
            TodoContract.TodoEntry.COLUMN_NAME_IS_CHECKED,
            TodoContract.TodoEntry.COLUMN_NAME_TIME_STAMP
        )
        val cursor = db.query(
            TodoContract.TodoEntry.TABLE_NAME,
            projection,
            null,
            null,
            null,
            null,
            null
        )
        return cursor.use {
            it.getTodoItem()
        }
    }
}