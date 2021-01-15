package com.survivalcoding.todolist.data.database

import android.content.ContentValues
import android.content.Context
import android.os.AsyncTask
import android.provider.BaseColumns
import com.survivalcoding.todolist.data.DefaultTodoRepository
import com.survivalcoding.todolist.model.TodoItem
import java.util.*

class TodoSqliteRepository(context: Context) : DefaultTodoRepository {

    private val dbHelper = TodoDatabaseHelper(context)

    var tempList = listOf<TodoItem>()

    // AsyncTask 사용하기
    override fun addItem(item: TodoItem) {
        InsertTodoTask(::addItemToDb).execute(item)
    }

    override fun removeItem(targetItem: TodoItem) {
        RemoveTodoTask(::removeItemFromDb).execute(targetItem)
    }

    override fun updateItem(item: TodoItem) {
        UpdateTodoTask(::updateItemOfDb).execute(item)
    }

    override fun getOrderedItems(): List<TodoItem> {
        tempList = GetOrderedItemsTask(::getOrderedItemsFromDb).execute().get()
        return tempList
    }

    override fun getFilteredItemsBy(keyword: String): List<TodoItem> =
        tempList.filter {
            it.title.toLowerCase(Locale.ROOT).contains(keyword.toLowerCase(Locale.ROOT))
        }

    class InsertTodoTask(private val addItem: (TodoItem) -> Unit) :
        AsyncTask<TodoItem, Any, Any>() {
        override fun doInBackground(vararg params: TodoItem?) {
            params[0]?.let {
                addItem.invoke(it)
            }
        }
    }

    class RemoveTodoTask(private val removeItem: (TodoItem) -> Unit) :
        AsyncTask<TodoItem, Any, Any>() {
        override fun doInBackground(vararg params: TodoItem?) {
            params[0]?.let {
                removeItem.invoke(it)
            }
        }
    }

    class UpdateTodoTask(private val updateItem: (TodoItem) -> Unit) :
        AsyncTask<TodoItem, Any, Any>() {
        override fun doInBackground(vararg params: TodoItem?) {
            params[0]?.let {
                updateItem.invoke(it)
            }
        }
    }

    class GetOrderedItemsTask(private val getOrderedItems: () -> List<TodoItem>) :
        AsyncTask<Any, Any, List<TodoItem>>() {
        override fun doInBackground(vararg params: Any?): List<TodoItem> {
            return getOrderedItems.invoke()
        }
    }

    private fun getOrderedItemsFromDb(): List<TodoItem> {
        val db = dbHelper.readableDatabase

        val projection =
            arrayOf(
                BaseColumns._ID,
                TodoContract.TodoEntry.COLUMN_NAME_TITLE,
                TodoContract.TodoEntry.COLUMN_NAME_TIMESTAMP,
                TodoContract.TodoEntry.COLUMN_NAME_IS_CHECKED,
                TodoContract.TodoEntry.COLUMN_NAME_CONTENT,
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
                val content =
                    getString(getColumnIndexOrThrow(TodoContract.TodoEntry.COLUMN_NAME_CONTENT))
                todoList.add(TodoItem(title, isChecked == 1, timeStamp, content, id))
            }
            close()
        }

        return todoList
    }

    private fun addItemToDb(item: TodoItem) {
        val db = dbHelper.writableDatabase

        val values = ContentValues().apply {
            put(TodoContract.TodoEntry.COLUMN_NAME_TITLE, item.title)
            put(TodoContract.TodoEntry.COLUMN_NAME_TIMESTAMP, item.timeStamp)
            put(TodoContract.TodoEntry.COLUMN_NAME_IS_CHECKED, item.isChecked)
            put(TodoContract.TodoEntry.COLUMN_NAME_CONTENT, item.content)
        }

        db.insert(TodoContract.TodoEntry.TABLE_NAME, null, values)
    }

    private fun removeItemFromDb(targetItem: TodoItem) {
        val db = dbHelper.writableDatabase

        val selection = "${BaseColumns._ID} = ?"
        val selectionArgs = arrayOf("${targetItem.id}")

        db.delete(TodoContract.TodoEntry.TABLE_NAME, selection, selectionArgs)
    }

    private fun updateItemOfDb(item: TodoItem) {
        val db = dbHelper.writableDatabase

        val values = ContentValues().apply {
            put(TodoContract.TodoEntry.COLUMN_NAME_TITLE, item.title)
            put(TodoContract.TodoEntry.COLUMN_NAME_TIMESTAMP, item.timeStamp)
            put(TodoContract.TodoEntry.COLUMN_NAME_IS_CHECKED, item.isChecked)
            put(TodoContract.TodoEntry.COLUMN_NAME_CONTENT, item.content)
        }

        val selection = "${BaseColumns._ID} = ?"
        val selectionArgs = arrayOf("${item.id}")
        db.update(TodoContract.TodoEntry.TABLE_NAME, values, selection, selectionArgs)
    }
}