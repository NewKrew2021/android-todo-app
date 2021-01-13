package com.survivalcoding.todolist.model

import android.content.ContentValues
import android.content.Context
import android.provider.BaseColumns
import com.survivalcoding.todolist.model.db.TodoContract
import com.survivalcoding.todolist.model.db.TodoDbHelper
import com.survivalcoding.todolist.util.getCurrentTime

class DbRepository(context: Context) : TodoRepository {
    private val dbHelper = TodoDbHelper(context)
    private val db = dbHelper.writableDatabase

    override fun addTodo(todoItem: TodoItem) {
        val newItem = ContentValues().apply {
            put(TodoContract.TodoEntry.COLUMN_NAME_TITLE, todoItem.todoTitle)
            put(TodoContract.TodoEntry.COLUMN_NAME_IS_CHECKED, todoItem.isChecked)
            put(TodoContract.TodoEntry.COLUMN_NAME_TIME_STAMP, todoItem.timeStamp)
        }
        db.insert(TodoContract.TodoEntry.TABLE_NAME, null, newItem)
    }

    private fun getTodoItems(): List<TodoItem> {
        val projection = arrayOf(
            BaseColumns._ID,
            TodoContract.TodoEntry.COLUMN_NAME_TITLE,
            TodoContract.TodoEntry.COLUMN_NAME_IS_CHECKED,
            TodoContract.TodoEntry.COLUMN_NAME_TIME_STAMP
        )
        val cursor = db.query(
            TodoContract.TodoEntry.TABLE_NAME,
            projection,
            null,
            null,
            null,
            null,
            null
        )

        val todoItems = mutableListOf<TodoItem>()
        cursor.use {
            with(it) {
                while (moveToNext()) {
                    val id = getInt(getColumnIndex(BaseColumns._ID))
                    val isChecked =
                        getInt(getColumnIndex(TodoContract.TodoEntry.COLUMN_NAME_IS_CHECKED))
                    val todoTitle =
                        getString(getColumnIndex(TodoContract.TodoEntry.COLUMN_NAME_TITLE))
                    val timeStamp =
                        getString(getColumnIndex(TodoContract.TodoEntry.COLUMN_NAME_TIME_STAMP))

                    todoItems.add(TodoItem(id, isChecked == 1, todoTitle, timeStamp))
                }
            }
        }
        return todoItems
    }

    override fun checkTodo(todoItem: TodoItem, isChecked: Boolean) {
        val db = dbHelper.writableDatabase

        val values = ContentValues().apply {
            put(TodoContract.TodoEntry.COLUMN_NAME_IS_CHECKED, todoItem.isChecked)
        }
        val selection =
            "${TodoContract.TodoEntry.COLUMN_NAME_TITLE} = ? AND ${TodoContract.TodoEntry.COLUMN_NAME_TIME_STAMP} = ?"
        val selectionArgs = arrayOf(todoItem.todoTitle, todoItem.timeStamp)
        val count = db.update(TodoContract.TodoEntry.TABLE_NAME, values, selection, selectionArgs)
    }

    override fun updateTodo(todoItem: TodoItem, newTodoTitle: String) {
        val db = dbHelper.writableDatabase

        val values = ContentValues().apply {
            put(TodoContract.TodoEntry.COLUMN_NAME_TITLE, newTodoTitle)
            put(TodoContract.TodoEntry.COLUMN_NAME_IS_CHECKED, todoItem.isChecked)
            put(TodoContract.TodoEntry.COLUMN_NAME_TIME_STAMP, getCurrentTime())
        }
        val selection =
            "${TodoContract.TodoEntry.COLUMN_NAME_TITLE} = ? AND ${TodoContract.TodoEntry.COLUMN_NAME_TIME_STAMP} = ?"
        val selectionArgs = arrayOf(todoItem.todoTitle, todoItem.timeStamp)
        val count = db.update(TodoContract.TodoEntry.TABLE_NAME, values, selection, selectionArgs)
    }

    override fun removeTodo(todoItem: TodoItem) {
        val db = dbHelper.writableDatabase

        val selection =
            "${TodoContract.TodoEntry.COLUMN_NAME_TITLE} = ? AND ${TodoContract.TodoEntry.COLUMN_NAME_TIME_STAMP} = ?"
        val selectionArgs = arrayOf(todoItem.todoTitle, todoItem.timeStamp)

        db.delete(TodoContract.TodoEntry.TABLE_NAME, selection, selectionArgs)
    }

    override fun getTodoListSortedByTime(): List<TodoItem> {
        return getTodoItems().sortedWith(compareBy<TodoItem> { it.isChecked }.thenByDescending { it.timeStamp })
    }

    override fun getTodoListSortedByTitle(): List<TodoItem> {
        return getTodoItems().sortedWith(compareBy<TodoItem> { it.isChecked }.thenBy { it.todoTitle })
    }

    override fun searchTodoItem(inputTitle: String): List<TodoItem> {
        val db = dbHelper.readableDatabase

        val projection = arrayOf(
            BaseColumns._ID,
            TodoContract.TodoEntry.COLUMN_NAME_TITLE,
            TodoContract.TodoEntry.COLUMN_NAME_IS_CHECKED,
            TodoContract.TodoEntry.COLUMN_NAME_TIME_STAMP
        )

        val selection = "LOWER(${TodoContract.TodoEntry.COLUMN_NAME_TITLE}) LIKE ?"
        val selectionArgs = arrayOf("%$inputTitle%")

        val cursor = db.query(
            TodoContract.TodoEntry.TABLE_NAME,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            null
        )

        val todoItems = mutableListOf<TodoItem>()
        cursor.use {
            with(it) {
                while (moveToNext()) {
                    val id = getInt(getColumnIndex(BaseColumns._ID))
                    val isChecked =
                        getInt(getColumnIndex(TodoContract.TodoEntry.COLUMN_NAME_IS_CHECKED))
                    val todoTitle =
                        getString(getColumnIndex(TodoContract.TodoEntry.COLUMN_NAME_TITLE))
                    val timeStamp =
                        getString(getColumnIndex(TodoContract.TodoEntry.COLUMN_NAME_TIME_STAMP))
                    todoItems.add(TodoItem(id, isChecked == 1, todoTitle, timeStamp))
                }
            }
        }
        return todoItems
    }

    override fun clearTodoList() {
        db.delete(TodoContract.TodoEntry.TABLE_NAME, null, null)
    }

    fun closeDb() {
        db.close()
    }
}