package com.survivalcoding.todolist.data.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.os.AsyncTask
import android.provider.BaseColumns
import com.survivalcoding.todolist.data.DefaultTodoRepository
import com.survivalcoding.todolist.view.main.model.Todo

class TodoDbRepository(context: Context) : DefaultTodoRepository {
    private val dbHelper = TodoDbHelper(context)
    private val readableDatabase by lazy { dbHelper.readableDatabase }
    private val writableDatabase by lazy { dbHelper.writableDatabase }

    override fun getOrderedItems(): List<Todo> = SelectAsyncTask(readableDatabase).execute().get()

    override fun getOrderedWithFilteredItems(query: String): List<Todo> = SelectFilteringAsyncTask(readableDatabase).execute(query).get()

    override fun add(todo: Todo) {
        InsertAsyncTask(writableDatabase).execute(ContentValues().setValues(todo))
    }

    override fun remove(todo: Todo) {
        DeleteAsyncTask(writableDatabase).execute(todo)
    }

    override fun removeAllRemovable() {
        DeleteAllRemovableAsyncTask(writableDatabase).execute(getOrderedItems())
    }

    override fun getRemovablesCount(): Int = getOrderedItems().count { it.isRemovable }

    override fun update(todo: Todo) {
        UpdateAsyncTask(writableDatabase).execute(todo)
    }

    class InsertAsyncTask(private val database: SQLiteDatabase) :
        AsyncTask<ContentValues, Unit, Unit>() {
        override fun doInBackground(vararg params: ContentValues?) {
            params.takeIf { it.isNotEmpty() }?.let {
                database.insert(TodoContract.TodoEntry.TABLE_NAME, null, it[0])
            }
        }
    }

    class DeleteAsyncTask(private val database: SQLiteDatabase) :
        AsyncTask<Todo, Unit, Unit>() {
        override fun doInBackground(vararg params: Todo?) {
            params.takeIf { it.isNotEmpty() }?.let {
                val selection = "${BaseColumns._ID} = ?"
                val selectionArgs = arrayOf("${it[0]?.id}")

                database.delete(TodoContract.TodoEntry.TABLE_NAME, selection, selectionArgs)
            }
        }
    }

    class DeleteAllRemovableAsyncTask(private val database: SQLiteDatabase) : AsyncTask<List<Todo>, Unit, Unit>() {
        override fun doInBackground(vararg params: List<Todo>?) {
            params.takeIf { it.isNotEmpty() }?.let {
                val removables = params[0]?.filter { it.isRemovable }

                removables?.let { items ->
                    val argsParameter  = Array(items.size) { '?' }.joinToString(",")
                    val selection = "${BaseColumns._ID} IN ($argsParameter)"
                    val selectionArgs = items.map { it.id.toString() }.toTypedArray()

                    database.delete(TodoContract.TodoEntry.TABLE_NAME, selection, selectionArgs)
                }
            }
        }
    }

    class SelectAsyncTask(private val database: SQLiteDatabase) :
        AsyncTask<Unit, Unit, List<Todo>>() {
        override fun doInBackground(vararg params: Unit?): List<Todo> {
            val cursor = database.query(
                TodoContract.TodoEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder,
            )

            val items = mutableListOf<Todo>()
            cursor.use {
                with(it) {
                    while (moveToNext()) {
                        items.add(
                            Todo(
                                id = it.getInt(getColumnIndex(BaseColumns._ID)),
                                title = getString(getColumnIndexOrThrow(TodoContract.TodoEntry.COLUMN_NAME_TITLE)),
                                times = getLong(getColumnIndexOrThrow(TodoContract.TodoEntry.COLUMN_NAME_TIMES)),
                                isDone = getInt(getColumnIndexOrThrow(TodoContract.TodoEntry.COLUMN_NAME_IS_DONE)) == 1,
                                isOption = getInt(getColumnIndexOrThrow(TodoContract.TodoEntry.COLUMN_NAME_IS_OPTION)) == 1,
                                isRemovable = getInt(getColumnIndexOrThrow(TodoContract.TodoEntry.COLUMN_NAME_IS_REMOVABLE)) == 1,
                            )
                        )
                    }
                }
            }
            return items
        }
    }

    class SelectFilteringAsyncTask(private val database: SQLiteDatabase)
        : AsyncTask<String, Unit, List<Todo>>() {
        override fun doInBackground(vararg params: String?): List<Todo> {
            return params.takeIf { it.isNotEmpty() }?.let {
                val selection = "${TodoContract.TodoEntry.COLUMN_NAME_TITLE} LIKE ?"
                val selectionArgs = arrayOf("%${it[0]}%")

                val cursor = database.query(
                    TodoContract.TodoEntry.TABLE_NAME,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    sortOrder,
                )

                val items = mutableListOf<Todo>()
                cursor.use { cursor ->
                    with(cursor) {
                        while (moveToNext()) {
                            items.add(
                                Todo(
                                    id = getInt(getColumnIndex(BaseColumns._ID)),
                                    title = getString(getColumnIndexOrThrow(TodoContract.TodoEntry.COLUMN_NAME_TITLE)),
                                    times = getLong(getColumnIndexOrThrow(TodoContract.TodoEntry.COLUMN_NAME_TIMES)),
                                    isDone = getInt(getColumnIndexOrThrow(TodoContract.TodoEntry.COLUMN_NAME_IS_DONE)) == 1,
                                    isOption = getInt(getColumnIndexOrThrow(TodoContract.TodoEntry.COLUMN_NAME_IS_OPTION)) == 1,
                                    isRemovable = getInt(getColumnIndexOrThrow(TodoContract.TodoEntry.COLUMN_NAME_IS_REMOVABLE)) == 1,
                                )
                            )
                        }
                    }
                }
                items
            } ?: listOf()
        }
    }

    class UpdateAsyncTask(private val database: SQLiteDatabase)
        : AsyncTask<Todo, Unit, Unit>() {
        override fun doInBackground(vararg params: Todo?) {
            params.takeIf { it.isNotEmpty() }?.let {
                val values = ContentValues().setValues(it[0])
                val selection = "${BaseColumns._ID} = ?"
                val selectionArgs = arrayOf("${it[0]?.id}")

                database.update(TodoContract.TodoEntry.TABLE_NAME, values, selection, selectionArgs)
            }
        }
    }

    companion object {
        private val projection = arrayOf(
            BaseColumns._ID,
            TodoContract.TodoEntry.COLUMN_NAME_TITLE,
            TodoContract.TodoEntry.COLUMN_NAME_TIMES,
            TodoContract.TodoEntry.COLUMN_NAME_IS_DONE,
            TodoContract.TodoEntry.COLUMN_NAME_IS_OPTION,
            TodoContract.TodoEntry.COLUMN_NAME_IS_REMOVABLE,
        )
        private const val sortOrder =
            "${TodoContract.TodoEntry.COLUMN_NAME_IS_DONE}, ${TodoContract.TodoEntry.COLUMN_NAME_TIMES} DESC"
    }
}

fun ContentValues.setValues(todo: Todo?): ContentValues {
    todo?.let {
        put(TodoContract.TodoEntry.COLUMN_NAME_TITLE, todo.title)
        put(TodoContract.TodoEntry.COLUMN_NAME_TIMES, todo.times)
        put(TodoContract.TodoEntry.COLUMN_NAME_IS_DONE, todo.isDone)
        put(TodoContract.TodoEntry.COLUMN_NAME_IS_OPTION, todo.isOption)
        put(TodoContract.TodoEntry.COLUMN_NAME_IS_REMOVABLE, todo.isRemovable)
    }
    return this
}