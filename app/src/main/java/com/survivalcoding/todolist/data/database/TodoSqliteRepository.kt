package com.survivalcoding.todolist.data.database

import android.content.ContentValues
import android.content.Context
import android.provider.BaseColumns
import com.survivalcoding.todolist.data.DefaultTodoRepository
import com.survivalcoding.todolist.model.TodoItem

class TodoSqliteRepository(context: Context) : DefaultTodoRepository {

    private val dbHelper = TodoDatabaseHelper(context)

    override fun getFilteredItemsBy(keyword: String): List<TodoItem> {
        return getOrderedItems().filter { it.title.contains(keyword) }
    }

    override fun getOrderedItems(): List<TodoItem> {
        val db = dbHelper.readableDatabase

        val projection =
            arrayOf(
                BaseColumns._ID,
                TodoContract.TodoEntry.COLUMN_NAME_TITLE,
                TodoContract.TodoEntry.COLUMN_NAME_TIMESTAMP,
                TodoContract.TodoEntry.COLUMN_NAME_IS_CHECKED,
            )

        val sortOrder =
            "${TodoContract.TodoEntry.COLUMN_NAME_IS_CHECKED} ASC, ${TodoContract.TodoEntry.COLUMN_NAME_TIMESTAMP} DESC"

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
                val title =
                    getString(getColumnIndexOrThrow(TodoContract.TodoEntry.COLUMN_NAME_TITLE))
                val isChecked =
                    getInt(getColumnIndexOrThrow(TodoContract.TodoEntry.COLUMN_NAME_IS_CHECKED))
                val timeStamp =
                    getLong(getColumnIndexOrThrow(TodoContract.TodoEntry.COLUMN_NAME_TIMESTAMP))
                todoList.add(TodoItem(title, isChecked == 1, timeStamp, id))
            }
            close()
        }

        return todoList
    }

    override fun addItem(item: TodoItem) {
        val db = dbHelper.writableDatabase

        val values = ContentValues().apply {
            put(TodoContract.TodoEntry.COLUMN_NAME_TITLE, item.title)
            put(TodoContract.TodoEntry.COLUMN_NAME_TIMESTAMP, item.timeStamp)
            put(TodoContract.TodoEntry.COLUMN_NAME_IS_CHECKED, item.isChecked)
        }

        db.insert(TodoContract.TodoEntry.TABLE_NAME, null, values)
    }

    override fun removeItem(targetItem: TodoItem) {
        val db = dbHelper.writableDatabase

        val selection = "${BaseColumns._ID} = ?"
        val selectionArgs = arrayOf("${targetItem.id}")

        db.delete(TodoContract.TodoEntry.TABLE_NAME, selection, selectionArgs)
    }

    override fun updateItem(item: TodoItem) {
        val db = dbHelper.writableDatabase

        val values = ContentValues().apply {
            put(TodoContract.TodoEntry.COLUMN_NAME_TITLE, item.title)
            put(TodoContract.TodoEntry.COLUMN_NAME_TIMESTAMP, item.timeStamp)
            put(TodoContract.TodoEntry.COLUMN_NAME_IS_CHECKED, item.isChecked)
        }

        val selection = "${BaseColumns._ID} = ?"
        val selectionArgs = arrayOf("${item.id}")
        db.update(TodoContract.TodoEntry.TABLE_NAME, values, selection, selectionArgs)
    }
}