package com.survivalcoding.todolist.model.db.asynctask

import android.database.sqlite.SQLiteDatabase
import android.os.AsyncTask
import android.provider.BaseColumns
import com.survivalcoding.todolist.model.TodoItem
import com.survivalcoding.todolist.model.db.TodoContract
import com.survivalcoding.todolist.util.getTodoItem

class SearchTodoAsyncTask(private val db: SQLiteDatabase) :
    AsyncTask<String, Unit, List<TodoItem>>() {
    override fun doInBackground(vararg params: String): List<TodoItem> {
        val searchKeyword = params[0]
        val projection = arrayOf(
            BaseColumns._ID,
            TodoContract.TodoEntry.COLUMN_NAME_TITLE,
            TodoContract.TodoEntry.COLUMN_NAME_IS_CHECKED,
            TodoContract.TodoEntry.COLUMN_NAME_TIME_STAMP
        )

        val selection = "LOWER(${TodoContract.TodoEntry.COLUMN_NAME_TITLE}) LIKE ?"
        val selectionArgs = arrayOf("%$searchKeyword%")

        val cursor = db.query(
            TodoContract.TodoEntry.TABLE_NAME,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            null
        )
        return cursor.use {
            it.getTodoItem().toList()
                .sortedWith(compareBy<TodoItem> { item -> item.isChecked }.thenBy { item -> item.todoTitle.length })
        }
    }
}