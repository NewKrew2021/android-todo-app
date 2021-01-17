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

    override fun search(title: String, updateUI: (List<Todo>) -> Unit) {
        val db = dbHelper.readableDatabase
        DatabaseSearchThread(db, title, updateUI).execute()
    }

    override fun sorting(
        sortingBase: SortingBase,
        orderMethod: OrderMethod,
        updateUI: (List<Todo>) -> Unit
    ) {
        val db = dbHelper.readableDatabase
        DatabaseSortThread(db, sortingBase, orderMethod, updateUI).execute()
    }

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
        private val updateUI: (List<Todo>) -> Unit
    ) :
        AsyncTask<Unit, Unit, List<Todo>>() {

        override fun doInBackground(vararg params: Unit?): List<Todo> {
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
                TodoContract.TodoEntry.TABLE_NAME, projection, null, null, null, null, sortOrder
            )

            val itemList = mutableListOf<Todo>()
            cursor.use {
                with(it) {
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
                }
            }
            return itemList.toList()
        }

        override fun onPostExecute(result: List<Todo>) {
            super.onPostExecute(result)
            updateUI.invoke(result)
        }
    }

    class DatabaseSearchThread(
        private val db: SQLiteDatabase,
        private val searchText: String,
        private val updateUI: (List<Todo>) -> Unit
    ) : AsyncTask<Unit, Unit, List<Todo>>() {
        override fun doInBackground(vararg params: Unit?): List<Todo> {
            val where = "${TodoContract.TodoEntry.COLUMN_NAME_TITLE} LIKE ?"
            val whereArgs = arrayOf("%${searchText}%")
            val cursor = db.query(
                TodoContract.TodoEntry.TABLE_NAME,
                null,
                where,
                whereArgs,
                null,
                null,
                null
            )

            val itemList = mutableListOf<Todo>()
            cursor.use {
                with(it) {
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
                }
            }
            return itemList.toList()
        }

        override fun onPostExecute(result: List<Todo>) {
            super.onPostExecute(result)
            updateUI.invoke(result)
        }
    }
}