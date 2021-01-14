package com.survivalcoding.todolist.model.db.asynctask

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.os.AsyncTask
import com.survivalcoding.todolist.model.TodoItem
import com.survivalcoding.todolist.model.db.TodoContract
import com.survivalcoding.todolist.util.getCurrentTime

data class EditTodoParam(val todoItem: TodoItem, val newTodoTitle: String)

class EditTodoAsyncTask(private val db: SQLiteDatabase) :
    AsyncTask<EditTodoParam, Unit, Unit>() {
    override fun doInBackground(vararg params: EditTodoParam) {
        val todoItem = params[0].todoItem
        val newTodoTitle = params[0].newTodoTitle

        val values = ContentValues().apply {
            put(TodoContract.TodoEntry.COLUMN_NAME_TITLE, newTodoTitle)
            put(TodoContract.TodoEntry.COLUMN_NAME_TIME_STAMP, getCurrentTime())
        }
        val selection =
            "${TodoContract.TodoEntry.COLUMN_NAME_TITLE} = ? AND ${TodoContract.TodoEntry.COLUMN_NAME_TIME_STAMP} = ?"
        val selectionArgs = arrayOf(todoItem.todoTitle, todoItem.timeStamp)
        db.update(TodoContract.TodoEntry.TABLE_NAME, values, selection, selectionArgs)
    }
}