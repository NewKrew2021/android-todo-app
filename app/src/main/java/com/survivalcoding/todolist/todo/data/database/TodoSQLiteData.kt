package com.survivalcoding.todolist.todo.data.database

import android.content.ContentValues
import android.content.Context
import android.provider.BaseColumns
import com.survivalcoding.todolist.todo.data.DefaultTodoData
import com.survivalcoding.todolist.todo.view.MainActivity
import com.survivalcoding.todolist.todo.view.model.Todo

class TodoSQLiteData(context: Context) : DefaultTodoData {
    private val dbHelper = TodoDbHelper(context)

    override fun addTodo(item: Todo) {
        val db = dbHelper.writableDatabase

        // Create a new map of values, where column names are the keys
        val values = ContentValues().apply {
            put(TodoContract.TodoEntry.COLUMN_NAME_IS_DONE, item.isDone)
            put(TodoContract.TodoEntry.COLUMN_NAME_TITLE, item.text)
            put(TodoContract.TodoEntry.COLUMN_NAME_DUE_DATE, item.dueDate)
            put(TodoContract.TodoEntry.COLUMN_NAME_WRITE_TIME, item.writeTime)
        }

        val newRowId = db?.insert(TodoContract.TodoEntry.TABLE_NAME, null, values)
    }

    override fun deleteTodo(item: Todo) {
        val db = dbHelper.writableDatabase

        val selection = "${BaseColumns._ID} LIKE ?"
        val selectionArgs = arrayOf("${item.id}")
        val deletedRows = db?.delete(TodoContract.TodoEntry.TABLE_NAME, selection, selectionArgs)
    }

    override fun updateTodo(item: Todo) {
        val db = dbHelper.writableDatabase

        val values = ContentValues().apply {
            put(TodoContract.TodoEntry.COLUMN_NAME_IS_DONE, item.isDone)
            put(TodoContract.TodoEntry.COLUMN_NAME_TITLE, item.text)
            put(TodoContract.TodoEntry.COLUMN_NAME_DUE_DATE, item.dueDate)
            put(TodoContract.TodoEntry.COLUMN_NAME_WRITE_TIME, item.writeTime)
        }

        val selection = "${BaseColumns._ID} LIKE ?"
        val selectionArgs = arrayOf("${item.id}")
        val updateRows =
            db?.update(TodoContract.TodoEntry.TABLE_NAME, values, selection, selectionArgs)

    }

    override fun sorting(sortingBase: Int, orderMethod: Int): MutableList<Todo> {
        val db = dbHelper.readableDatabase

        val projection = arrayOf(
            BaseColumns._ID,
            TodoContract.TodoEntry.COLUMN_NAME_IS_DONE,
            TodoContract.TodoEntry.COLUMN_NAME_TITLE,
            TodoContract.TodoEntry.COLUMN_NAME_DUE_DATE,
            TodoContract.TodoEntry.COLUMN_NAME_WRITE_TIME
        )

        val order = if (orderMethod == MainActivity.ASCENDING) "" else " DESC"
        val sort = when (sortingBase) {
            MainActivity.SORT_BY_TITLE -> "${TodoContract.TodoEntry.COLUMN_NAME_TITLE}"
            MainActivity.SORT_BY_D_DAY -> "${TodoContract.TodoEntry.COLUMN_NAME_DUE_DATE}"
            MainActivity.SORT_BY_DATE -> "${TodoContract.TodoEntry.COLUMN_NAME_WRITE_TIME}"
            else -> "${TodoContract.TodoEntry.COLUMN_NAME_TITLE}"
        }
        val sortOrder = "$sort$order"

        val cursor = db.query(
            TodoContract.TodoEntry.TABLE_NAME,   // The table to query
            projection,             // The array of columns to return (pass null to get all)
            null,              // The columns for the WHERE clause
            null,          // The values for the WHERE clause
            null,                   // don't group the rows
            null,                   // don't filter by row groups
            sortOrder               // The sort order
        )

        val itemList = mutableListOf<Todo>()
        with(cursor) {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(BaseColumns._ID))
                val text =
                    getString(getColumnIndexOrThrow(TodoContract.TodoEntry.COLUMN_NAME_TITLE))
                val isDone =
                    getInt(getColumnIndexOrThrow(TodoContract.TodoEntry.COLUMN_NAME_IS_DONE))
                val dueDate =
                    getLong(getColumnIndexOrThrow(TodoContract.TodoEntry.COLUMN_NAME_DUE_DATE))
                val writeTime =
                    getLong(getColumnIndexOrThrow(TodoContract.TodoEntry.COLUMN_NAME_WRITE_TIME))
                itemList.add(Todo(isDone == 1, text, dueDate, writeTime, id))
            }
            close()
        }
        return itemList
    }

}