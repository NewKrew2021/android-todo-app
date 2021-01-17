package com.survivalcoding.todolist.data.db.task

import android.content.ContentValues
import android.os.AsyncTask
import com.survivalcoding.todolist.data.db.TodoContract
import com.survivalcoding.todolist.data.db.TodoDbHelper

class UpdateItemAsyncTask(
    private val dbHelper: TodoDbHelper, private val values: ContentValues,
    private val selection: String,
    private val selectionArgs: Array<String>
) :
    AsyncTask<Unit, Unit, Unit>() {
    override fun doInBackground(vararg params: Unit?) {
        val db = dbHelper.writableDatabase
        db.update(
            TodoContract.TodoEntry.TABLE_NAME,
            values,
            selection,
            selectionArgs
        )
    }
}
