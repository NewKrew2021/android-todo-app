package com.survivalcoding.todolist.data.db.task

import android.os.AsyncTask
import android.provider.BaseColumns
import com.survivalcoding.todolist.data.db.TodoContract
import com.survivalcoding.todolist.data.db.TodoDbHelper
import com.survivalcoding.todolist.view.main.model.TodoData

class GetItemAsyncTask(
    private val dbHelper: TodoDbHelper,
    private val saveDB: MutableList<TodoData>,
    val projection: Array<String>,
    val sortOrder: String
) : AsyncTask<Unit, Unit, Unit>() {
    override fun onPreExecute() {

    }

    override fun doInBackground(vararg params: Unit?) {

        val db = dbHelper.readableDatabase


        val cursor = db.query(
            TodoContract.TodoEntry.TABLE_NAME,   // The table to query
            projection,             // The array of columns to return (pass null to get all)
            null,              // The columns for the WHERE clause
            null,          // The values for the WHERE clause
            null,                   // don't group the rows
            null,                   // don't filter by row groups
            sortOrder               // The sort order
        )

        val items = mutableListOf<TodoData>()
        with(cursor) {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(BaseColumns._ID))
                val todo =
                    getString(getColumnIndexOrThrow(TodoContract.TodoEntry.COLUMN_NAME_TEXT))
                val time =
                    getLong(getColumnIndexOrThrow(TodoContract.TodoEntry.COLUMN_NAME_TIME))
                val isDone =
                    getInt(getColumnIndexOrThrow(TodoContract.TodoEntry.COLUMN_NAME_IS_DONE))
                items.add(TodoData(todo, time, isDone == 1, id))
            }
            close()
        }
        saveDB.clear()
        saveDB.addAll(items)
    }
}