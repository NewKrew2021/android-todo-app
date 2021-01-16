package com.survivalcoding.todolist.data.db.task

import android.content.ContentValues
import android.os.AsyncTask
import com.survivalcoding.todolist.data.db.TodoContract
import com.survivalcoding.todolist.data.db.TodoDbHelper

class AddItemAsyncTask(private val dbHelper: TodoDbHelper, private val values: ContentValues) :
    AsyncTask<Unit, Unit, Unit>() {
    override fun doInBackground(vararg params: Unit?) {
        val db = dbHelper.writableDatabase
        db?.insert(TodoContract.TodoEntry.TABLE_NAME, null, values)
    }
}