package com.survivalcoding.todolist.data.db

import android.content.ContentValues
import android.content.Context
import android.provider.BaseColumns
import com.survivalcoding.todolist.data.DefaultTodoRepository
import com.survivalcoding.todolist.view.main.model.Todo

class TodoSqliteRepository(context: Context) : DefaultTodoRepository {
    private val dbHelper = TodoDbHelper(context)

    override fun getOrderedItems(): List<Todo> {
        val db = dbHelper.readableDatabase

        val projection =
            arrayOf(
                BaseColumns._ID,
                TodoContract.TodoEntry.COLUMN_NAME_TODO,
                TodoContract.TodoEntry.COLUMN_NAME_DATETIME,
                TodoContract.TodoEntry.COLUMN_NAME_IS_DONE,
            )

        val sortOrder = "${TodoContract.TodoEntry.COLUMN_NAME_DATETIME} DESC"

        val cursor = db.query(
            TodoContract.TodoEntry.TABLE_NAME,   // The table to query
            projection,             // The array of columns to return (pass null to get all)
            null,              // The columns for the WHERE clause
            null,          // The values for the WHERE clause
            null,                   // don't group the rows
            null,                   // don't filter by row groups
            sortOrder               // The sort order
        )

        val todos = mutableListOf<Todo>()
        with(cursor) {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(BaseColumns._ID))
                val todo = getString(getColumnIndexOrThrow(TodoContract.TodoEntry.COLUMN_NAME_TODO))
                val datetime =
                    getLong(getColumnIndexOrThrow(TodoContract.TodoEntry.COLUMN_NAME_DATETIME))
                val isDone =
                    getInt(getColumnIndexOrThrow(TodoContract.TodoEntry.COLUMN_NAME_IS_DONE))
                todos.add(Todo(todo, datetime, isDone == 1, id))
            }
            close()
        }

        return todos
    }

    override fun addTodo(item: Todo) {
        val db = dbHelper.writableDatabase

        val values = ContentValues().apply {
            put(TodoContract.TodoEntry.COLUMN_NAME_TODO, item.todo)
            put(TodoContract.TodoEntry.COLUMN_NAME_DATETIME, item.datetime)
            put(TodoContract.TodoEntry.COLUMN_NAME_IS_DONE, item.isDone)
        }

        val newRowId = db?.insert(TodoContract.TodoEntry.TABLE_NAME, null, values)
    }

    override fun updateTodo(item: Todo) {
        val db = dbHelper.writableDatabase

        // New value for one column
        val values = ContentValues().apply {
            put(TodoContract.TodoEntry.COLUMN_NAME_TODO, item.todo)
            put(TodoContract.TodoEntry.COLUMN_NAME_DATETIME, item.datetime)
            put(TodoContract.TodoEntry.COLUMN_NAME_IS_DONE, item.isDone)
        }

        // Which row to update, based on the title
        val selection = "${BaseColumns._ID} = ?"
        val selectionArgs = arrayOf("${item.id}")
        val count = db.update(
            TodoContract.TodoEntry.TABLE_NAME,
            values,
            selection,
            selectionArgs
        )
    }

    override fun removeTodo(item: Todo) {
        val db = dbHelper.writableDatabase

        val selection = "${BaseColumns._ID} = ?"
        val selectionArgs = arrayOf("${item.id}")
        val deletedRows = db.delete(TodoContract.TodoEntry.TABLE_NAME, selection, selectionArgs)
    }

}