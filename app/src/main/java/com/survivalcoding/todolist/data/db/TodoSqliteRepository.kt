package com.survivalcoding.todolist.data.db

import android.content.ContentValues
import android.content.Context
import android.provider.BaseColumns
import com.survivalcoding.todolist.data.TodoDefaultRepository
import com.survivalcoding.todolist.view.main.model.TodoData
import java.util.*

class TodoSqliteRepository(context: Context) : TodoDefaultRepository {

    private val dbHelper = TodoDbHelper(context)
    override fun getItems(): List<TodoData> {
        val db = dbHelper.readableDatabase

        val projection =
            arrayOf(
                BaseColumns._ID,
                TodoContract.TodoEntry.COLUMN_NAME_TEXT,
                TodoContract.TodoEntry.COLUMN_NAME_TIME,
                TodoContract.TodoEntry.COLUMN_NAME_IS_DONE,
            )

        val sortOrder = "${TodoContract.TodoEntry.COLUMN_NAME_TIME} DESC"

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
                val todo = getString(getColumnIndexOrThrow(TodoContract.TodoEntry.COLUMN_NAME_TEXT))
                val time =
                    getLong(getColumnIndexOrThrow(TodoContract.TodoEntry.COLUMN_NAME_TIME))
                val isDone =
                    getInt(getColumnIndexOrThrow(TodoContract.TodoEntry.COLUMN_NAME_IS_DONE))
                items.add(TodoData(todo, time, isDone == 1, id))
            }
            close()
        }
        return items.toList()
    }

    override fun addItem(data: TodoData) {
        //To-Do 아이템 추가
        val db = dbHelper.writableDatabase

        data.time = Calendar.getInstance().timeInMillis

        val values = ContentValues().apply {
            put(TodoContract.TodoEntry.COLUMN_NAME_TEXT, data.text)
            put(TodoContract.TodoEntry.COLUMN_NAME_TIME, data.time)
            put(TodoContract.TodoEntry.COLUMN_NAME_IS_DONE, data.isDone)
        }

        db?.insert(TodoContract.TodoEntry.TABLE_NAME, null, values)
    }

    override fun delItem(data: TodoData) {
        val db = dbHelper.writableDatabase

        val selection = "${BaseColumns._ID} = ?"
        val selectionArgs = arrayOf("${data.pid}")
        db.delete(TodoContract.TodoEntry.TABLE_NAME, selection, selectionArgs)
    }

    override fun addAllItems(data: List<TodoData>) {
    }

}