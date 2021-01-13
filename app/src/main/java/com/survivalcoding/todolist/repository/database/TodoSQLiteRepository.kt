package com.survivalcoding.todolist.repository.database

import android.content.ContentValues
import android.content.Context
import android.provider.BaseColumns
import com.survivalcoding.todolist.model.TodoItem
import com.survivalcoding.todolist.repository.DefaultTodoRepository

class TodoSQLiteRepository(context: Context) : DefaultTodoRepository {


    private val dbHelper = TodoDbHelper(context)

    override fun getOrderedItems(): List<TodoItem> {
        val db = dbHelper.readableDatabase

        val projection =
                arrayOf(
                        BaseColumns._ID,
                        TodoContract.TodoEntry.COLUMN_NAME_TITLE,
                        TodoContract.TodoEntry.COLUMN_NAME_DATE,
                        TodoContract.TodoEntry.COLUMN_NAME_DONE,
                        TodoContract.TodoEntry.COLUMN_NAME_MARK,
                )

        val sortOrder = "${TodoContract.TodoEntry.COLUMN_NAME_DONE} ASC, ${TodoContract.TodoEntry.COLUMN_NAME_MARK} DESC, ${TodoContract.TodoEntry.COLUMN_NAME_DATE} ASC"

        val cursor = db.query(
                TodoContract.TodoEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        )
        val todoList = mutableListOf<TodoItem>()
        with(cursor) {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(BaseColumns._ID))
                val title = getString(getColumnIndexOrThrow(TodoContract.TodoEntry.COLUMN_NAME_TITLE))
                val date = getLong(getColumnIndexOrThrow(TodoContract.TodoEntry.COLUMN_NAME_DATE))
                val isComplete = getInt(getColumnIndexOrThrow(TodoContract.TodoEntry.COLUMN_NAME_DONE))
                val isMark = getInt(getColumnIndexOrThrow(TodoContract.TodoEntry.COLUMN_NAME_MARK))
                todoList.add(TodoItem(title, date, isComplete == 1, isMark == 1, id))
            }
            close()
        }

        return todoList
    }

    override fun add(item: TodoItem) {
        val db = dbHelper.writableDatabase

        val values = ContentValues().apply {
            put(TodoContract.TodoEntry.COLUMN_NAME_TITLE, item.title)
            put(TodoContract.TodoEntry.COLUMN_NAME_DATE, item.date)
            put(TodoContract.TodoEntry.COLUMN_NAME_DONE, item.isComplete)
            put(TodoContract.TodoEntry.COLUMN_NAME_MARK, item.isMark)
        }
        db?.insert(TodoContract.TodoEntry.TABLE_NAME, null, values)
    }

    override fun remove(item: TodoItem) {
        val db = dbHelper.writableDatabase

        val selection = "${BaseColumns._ID} = ?"
        val selectionArgs = arrayOf("${item.id}")
        db.delete(TodoContract.TodoEntry.TABLE_NAME, selection, selectionArgs)
    }

    override fun update(item: TodoItem) {
        val db = dbHelper.writableDatabase

        val values = ContentValues().apply {
            put(TodoContract.TodoEntry.COLUMN_NAME_TITLE, item.title)
            put(TodoContract.TodoEntry.COLUMN_NAME_DATE, item.date)
            put(TodoContract.TodoEntry.COLUMN_NAME_DONE, item.isComplete)
            put(TodoContract.TodoEntry.COLUMN_NAME_MARK, item.isMark)
        }

        val selection = "${BaseColumns._ID} = ?"
        val selectionArgs = arrayOf("${item.id}")

        db.update(TodoContract.TodoEntry.TABLE_NAME, values, selection, selectionArgs)
    }

}