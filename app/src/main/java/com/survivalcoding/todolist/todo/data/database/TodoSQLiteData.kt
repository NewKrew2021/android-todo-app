package com.survivalcoding.todolist.todo.data.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.os.AsyncTask
import android.provider.BaseColumns
import com.survivalcoding.todolist.todo.data.DefaultTodoData
import com.survivalcoding.todolist.todo.view.MainActivity
import com.survivalcoding.todolist.todo.view.OrderMethod
import com.survivalcoding.todolist.todo.view.SortingBase
import com.survivalcoding.todolist.todo.view.model.Todo

class TodoSQLiteData(context: Context) : DefaultTodoData {
    private val dbHelper = TodoDbHelper(context)

    private val db by lazy { dbHelper.writableDatabase }

    override fun addTodo(item: Todo) {
        DatabaseAddThread(db).execute(item)
    }

    override fun deleteTodo(item: Todo) {
        DatabaseRemoveThread(db).execute(item)
    }

    override fun updateTodo(item: Todo) {
        DatabaseUpdateThread(db).execute(item)
    }

    // 여기서 값을 리턴할 수 없다면, submitList메소드를 MainFragment에서 받아와서 onPostExecute에서 submitList메소드를 실행하자.
    override fun sorting(
        sortingBase: SortingBase,
        orderMethod: OrderMethod,
        updateUI: (MutableList<Todo>) -> Unit
    ) {
        val db = dbHelper.readableDatabase
        DatabaseSortThread(db, sortingBase, orderMethod, updateUI).execute()
    }

    // 4개의 클래스로 처리하는 것이 좋은가..?
    // 아니면 하나의 클래스의 doInBackground에서 when문으로 처리하는것이 좋을까?
    class DatabaseAddThread(private val db: SQLiteDatabase) : AsyncTask<Todo, Unit, Long>() {
        override fun doInBackground(vararg params: Todo): Long {
            val item = params[0]

            // Create a new map of values, where column names are the keys
            val values = ContentValues().apply {
                put(TodoContract.TodoEntry.COLUMN_NAME_IS_DONE, item.isDone)
                put(TodoContract.TodoEntry.COLUMN_NAME_TITLE, item.text)
                put(TodoContract.TodoEntry.COLUMN_NAME_DUE_DATE, item.dueDate)
                put(TodoContract.TodoEntry.COLUMN_NAME_WRITE_TIME, item.writeTime)
            }

            return db?.insert(TodoContract.TodoEntry.TABLE_NAME, null, values)
        }

        override fun onPostExecute(result: Long?) {
            super.onPostExecute(result)
        }
    }

    class DatabaseRemoveThread(private val db: SQLiteDatabase) : AsyncTask<Todo, Unit, Unit>() {
        override fun doInBackground(vararg params: Todo) {
            val item = params[0]
            val selection = "${BaseColumns._ID} = ?"
            val selectionArgs = arrayOf("${item.id}")
            val deletedRows =
                db?.delete(TodoContract.TodoEntry.TABLE_NAME, selection, selectionArgs)
        }
    }

    class DatabaseUpdateThread(private val db: SQLiteDatabase) : AsyncTask<Todo, Unit, Unit>() {
        override fun doInBackground(vararg params: Todo) {
            val item = params[0]

            val values = ContentValues().apply {
                put(TodoContract.TodoEntry.COLUMN_NAME_IS_DONE, item.isDone)
                put(TodoContract.TodoEntry.COLUMN_NAME_TITLE, item.text)
                put(TodoContract.TodoEntry.COLUMN_NAME_DUE_DATE, item.dueDate)
                put(TodoContract.TodoEntry.COLUMN_NAME_WRITE_TIME, item.writeTime)
            }

            val selection = "${BaseColumns._ID} = ?"
            val selectionArgs = arrayOf("${item.id}")
            val updateRows =
                db?.update(TodoContract.TodoEntry.TABLE_NAME, values, selection, selectionArgs)
        }
    }

    class DatabaseSortThread(
        private val db: SQLiteDatabase,
        private val sortingBase: SortingBase,
        private val orderMethod: OrderMethod,
        private val updateUI: (MutableList<Todo>) -> Unit
    ) :
        AsyncTask<Unit, Unit, MutableList<Todo>>() {

        override fun doInBackground(vararg params: Unit?): MutableList<Todo> {
            val projection = arrayOf(
                BaseColumns._ID,
                TodoContract.TodoEntry.COLUMN_NAME_IS_DONE,
                TodoContract.TodoEntry.COLUMN_NAME_TITLE,
                TodoContract.TodoEntry.COLUMN_NAME_DUE_DATE,
                TodoContract.TodoEntry.COLUMN_NAME_WRITE_TIME
            )

            val order = if (orderMethod.value == MainActivity.ASCENDING) "" else " DESC"
            val sort = when (sortingBase.value) {
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

        override fun onPostExecute(result: MutableList<Todo>) {
            super.onPostExecute(result)
            updateUI.invoke(result)
        }
    }
}