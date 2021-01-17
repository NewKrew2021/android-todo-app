package com.survivalcoding.todolist.data.db

import android.content.ContentValues
import android.content.Context
import android.provider.BaseColumns
import com.survivalcoding.todolist.data.TodoRepository
import com.survivalcoding.todolist.data.db.task.AddItemAsyncTask
import com.survivalcoding.todolist.data.db.task.DelItemAsyncTask
import com.survivalcoding.todolist.data.db.task.GetItemAsyncTask
import com.survivalcoding.todolist.data.db.task.UpdateItemAsyncTask
import com.survivalcoding.todolist.view.main.model.TodoData
import java.util.*

class TodoDbRepository(context: Context) : TodoRepository {

    private val dbHelper = TodoDbHelper(context)
    private val saveDB = mutableListOf<TodoData>()
    override fun getItems(): List<TodoData> {
        val sortOrder =
            "${TodoContract.TodoEntry.COLUMN_NAME_IS_DONE} ASC, ${TodoContract.TodoEntry.COLUMN_NAME_TIME} DESC"
        val projection =
            arrayOf(
                BaseColumns._ID,
                TodoContract.TodoEntry.COLUMN_NAME_TEXT,
                TodoContract.TodoEntry.COLUMN_NAME_TIME,
                TodoContract.TodoEntry.COLUMN_NAME_IS_DONE,
            )
        GetItemAsyncTask(dbHelper, saveDB, projection, sortOrder).execute().get()
        return saveDB
    }

    override fun addItem(data: TodoData) {
        //To-Do 아이템 추가

        data.time = Calendar.getInstance().timeInMillis

        val values = ContentValues().apply {
            put(TodoContract.TodoEntry.COLUMN_NAME_TEXT, data.text)
            put(TodoContract.TodoEntry.COLUMN_NAME_TIME, data.time)
            put(TodoContract.TodoEntry.COLUMN_NAME_IS_DONE, data.isDone)
        }
        AddItemAsyncTask(dbHelper, values).execute()

    }

    override fun delItem(data: TodoData) {

        val selection = "${BaseColumns._ID} = ?"
        val selectionArgs = arrayOf("${data.pid}")
        DelItemAsyncTask(dbHelper, selection, selectionArgs).execute()
    }

    override fun editItem(data: TodoData, changeTodo: String) {
        data.time = Calendar.getInstance().timeInMillis
        data.text = changeTodo
        val values = ContentValues().apply {
            put(TodoContract.TodoEntry.COLUMN_NAME_TEXT, data.text)
            put(TodoContract.TodoEntry.COLUMN_NAME_TIME, data.time)
            put(TodoContract.TodoEntry.COLUMN_NAME_IS_DONE, data.isDone)
        }
        val selection = "${BaseColumns._ID} = ?"
        val selectionArgs = arrayOf("${data.pid}")
        UpdateItemAsyncTask(dbHelper, values, selection, selectionArgs).execute()

    }

    override fun doneItem(data: TodoData) {
        data.isDone = !data.isDone
        val values = ContentValues().apply {
            put(TodoContract.TodoEntry.COLUMN_NAME_TEXT, data.text)
            put(TodoContract.TodoEntry.COLUMN_NAME_TIME, data.time)
            put(TodoContract.TodoEntry.COLUMN_NAME_IS_DONE, data.isDone)
        }
        val selection = "${BaseColumns._ID} = ?"
        val selectionArgs = arrayOf("${data.pid}")
        UpdateItemAsyncTask(dbHelper, values, selection, selectionArgs).execute()
    }


}