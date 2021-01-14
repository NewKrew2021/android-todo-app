package com.survivalcoding.todolist.model.db.asynctask

import android.database.sqlite.SQLiteDatabase
import android.os.AsyncTask
import com.survivalcoding.todolist.model.TodoItem
import com.survivalcoding.todolist.model.db.TodoContract

class RemoveTodoAsyncTask(private val db: SQLiteDatabase) : AsyncTask<TodoItem, Unit, Unit>() {
    override fun doInBackground(vararg params: TodoItem) {
        val todoItem = params[0]
        val selection =
            "${TodoContract.TodoEntry.COLUMN_NAME_TITLE} = ? AND ${TodoContract.TodoEntry.COLUMN_NAME_TIME_STAMP} = ?"
        val selectionArgs = arrayOf(todoItem.todoTitle, todoItem.timeStamp)

        db.delete(TodoContract.TodoEntry.TABLE_NAME, selection, selectionArgs)
    }
}