package com.survivalcoding.todolist.model.db.asynctask

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.os.AsyncTask
import com.survivalcoding.todolist.model.TodoItem
import com.survivalcoding.todolist.model.db.TodoContract

data class CheckTodoParam(val todoItem: TodoItem, val isChecked: Boolean)

class CheckTodoAsyncTask(private val db: SQLiteDatabase) :
    AsyncTask<CheckTodoParam, Unit, Unit>() {
    override fun doInBackground(vararg params: CheckTodoParam) {
        val todoItem = params[0].todoItem
        val isChecked = params[0].isChecked

        val values = ContentValues().apply {
            put(TodoContract.TodoEntry.COLUMN_NAME_IS_CHECKED, isChecked)
        }
        val selection =
            "${TodoContract.TodoEntry.COLUMN_NAME_TITLE} = ? AND ${TodoContract.TodoEntry.COLUMN_NAME_TIME_STAMP} = ?"
        val selectionArgs = arrayOf(todoItem.todoTitle, todoItem.timeStamp)
        db.update(TodoContract.TodoEntry.TABLE_NAME, values, selection, selectionArgs)
    }
}