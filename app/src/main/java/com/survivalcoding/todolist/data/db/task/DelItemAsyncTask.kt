package com.survivalcoding.todolist.data.db.task

import android.os.AsyncTask
import com.survivalcoding.todolist.data.db.TodoContract
import com.survivalcoding.todolist.data.db.TodoDbHelper

class DelItemAsyncTask(
    private val dbHelper: TodoDbHelper,
    private val selection: String,
    private val selectionArgs: Array<String>
) : AsyncTask<Unit, Unit, Unit>() {
    override fun onPreExecute() {

    }

    override fun doInBackground(vararg params: Unit?) {
        val db = dbHelper.writableDatabase
        db.delete(TodoContract.TodoEntry.TABLE_NAME, selection, selectionArgs)
    }
}