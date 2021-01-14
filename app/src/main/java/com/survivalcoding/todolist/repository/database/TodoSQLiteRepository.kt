package com.survivalcoding.todolist.repository.database

import android.content.ContentValues
import android.content.Context
import android.os.AsyncTask
import android.provider.BaseColumns
import com.survivalcoding.todolist.extension.setData
import com.survivalcoding.todolist.model.TodoItem
import com.survivalcoding.todolist.repository.DefaultTodoRepository

class TodoSQLiteRepository(context: Context) : DefaultTodoRepository {


    private val dbHelper = TodoDbHelper(context)
    override fun getOrderedItems(callback: MyCallback){
        GetTask().execute(dbHelper, callback)
    }

      class GetTask() : AsyncTask<Any, Unit, Pair<List<TodoItem>, MyCallback>>(){
        override fun doInBackground(vararg params: Any) : Pair<List<TodoItem>, MyCallback> {
            val db = (params[0] as TodoDbHelper).readableDatabase
            val callback = (params[1] as MyCallback)
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

            return Pair(todoList, callback)
        }

        override fun onPostExecute(result: Pair<List<TodoItem>, MyCallback>) {
            super.onPostExecute(result)
            result.second.getListCallBack(result.first)
        }
    }

    override fun add(item: TodoItem) {
       AddTask().execute(dbHelper, item)
    }

    class AddTask() : AsyncTask<Any, Unit, Unit>(){
        override fun doInBackground(vararg params: Any) {
            val db = (params[0] as TodoDbHelper).writableDatabase
            val values = ContentValues().setData(params[1] as TodoItem)
            db?.insert(TodoContract.TodoEntry.TABLE_NAME, null, values)
        }
    }
    override fun remove(item: TodoItem) {
        RemoveTask().execute(dbHelper, item)
    }

    class RemoveTask() : AsyncTask<Any, Unit, Unit>(){
        override fun doInBackground(vararg params: Any) {
            val db = (params[0] as TodoDbHelper).writableDatabase

            val selection = "${BaseColumns._ID} = ?"
            val selectionArgs = arrayOf("${(params[1] as TodoItem).id}")
            db.delete(TodoContract.TodoEntry.TABLE_NAME, selection, selectionArgs)
        }
    }

    override fun update(item: TodoItem) {
        UpdateTask().execute(dbHelper, item)
    }

    class UpdateTask() : AsyncTask<Any, Unit, Unit>(){
        override fun doInBackground(vararg params: Any) {
            val db = (params[0] as TodoDbHelper).writableDatabase
            val values = ContentValues().setData(params[1] as TodoItem)

            val selection = "${BaseColumns._ID} = ?"
            val selectionArgs = arrayOf("${(params[1] as TodoItem).id}")

            db.update(TodoContract.TodoEntry.TABLE_NAME, values, selection, selectionArgs)
        }
    }


}

interface MyCallback{
    fun getListCallBack(list : List<TodoItem>)
}