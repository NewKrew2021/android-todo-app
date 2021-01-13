package com.survivalcoding.todolist.data.db

import android.content.ContentValues
import android.content.Context
import android.provider.BaseColumns
import com.survivalcoding.todolist.data.DefaultTodoRepository
import com.survivalcoding.todolist.view.main.model.Todo

class TodoSqliteRepository(context: Context) : DefaultTodoRepository {
    private val dbHelper = TodoDbHelper(context)

    private val projection =  arrayOf(
        BaseColumns._ID,
        TodoContract.TodoEntry.COLUMN_NAME_TITLE,
        TodoContract.TodoEntry.COLUMN_NAME_TIMES,
        TodoContract.TodoEntry.COLUMN_NAME_IS_DONE,
        TodoContract.TodoEntry.COLUMN_NAME_IS_OPTION,
        TodoContract.TodoEntry.COLUMN_NAME_IS_REMOVABLE,
    )

    private val sortOrder = "${TodoContract.TodoEntry.COLUMN_NAME_IS_DONE}, ${TodoContract.TodoEntry.COLUMN_NAME_TIMES} DESC"

    override fun getOrderedItems(): List<Todo> {
        val db = dbHelper.readableDatabase

        val cursor = db.query(
            TodoContract.TodoEntry.TABLE_NAME,
            projection,
            null,
            null,
            null,
            null,
            sortOrder,
        )

        val items = mutableListOf<Todo>()
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
            close()
        }

        return items
    }

    override fun getOrderedWithFilteredItems(query: String): List<Todo> {
        val db = dbHelper.readableDatabase

        val selection = "${TodoContract.TodoEntry.COLUMN_NAME_TITLE} LIKE ?"
        val selectionArgs = arrayOf("%$query%")

        val cursor = db.query(
            TodoContract.TodoEntry.TABLE_NAME,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            sortOrder,
        )

        val items = mutableListOf<Todo>()
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
            close()
        }

        return items
    }

    override fun add(todo: Todo) {
        val db = dbHelper.writableDatabase

        val values = ContentValues().apply {
            put(TodoContract.TodoEntry.COLUMN_NAME_TITLE, todo.title)
            put(TodoContract.TodoEntry.COLUMN_NAME_TIMES, todo.times)
            put(TodoContract.TodoEntry.COLUMN_NAME_IS_DONE, todo.isDone)
            put(TodoContract.TodoEntry.COLUMN_NAME_IS_OPTION, todo.isOption)
            put(TodoContract.TodoEntry.COLUMN_NAME_IS_REMOVABLE, todo.isRemovable)
        }

        db.insert(TodoContract.TodoEntry.TABLE_NAME, null, values)
    }

    override fun remove(todo: Todo) {
        val db = dbHelper.writableDatabase

        val selection = "${BaseColumns._ID} = ?"
        val selectionArgs = arrayOf("${todo.id}")

        db.delete(TodoContract.TodoEntry.TABLE_NAME, selection, selectionArgs)
    }

    override fun removeAllRemovable() {
        val db = dbHelper.writableDatabase

        val removables = getOrderedItems().filter { it.isRemovable }
        val argsParameter = Array(removables.size) { '?' }.joinToString(",")

        val selection = "${BaseColumns._ID} IN ($argsParameter)"
        val selectionArgs = removables.map { it.id.toString() }.toTypedArray()

        db.delete(TodoContract.TodoEntry.TABLE_NAME, selection, selectionArgs)
    }

    override fun getRemovablesCount(): Int = getOrderedItems().count { it.isRemovable }

    override fun update(todo: Todo) {
        val db = dbHelper.writableDatabase

        val values = ContentValues().apply {
            put(TodoContract.TodoEntry.COLUMN_NAME_TITLE, todo.title)
            put(TodoContract.TodoEntry.COLUMN_NAME_TIMES, todo.times)
            put(TodoContract.TodoEntry.COLUMN_NAME_IS_DONE, todo.isDone)
            put(TodoContract.TodoEntry.COLUMN_NAME_IS_OPTION, todo.isOption)
            put(TodoContract.TodoEntry.COLUMN_NAME_IS_REMOVABLE, todo.isRemovable)
        }

        val selection = "${BaseColumns._ID} = ?"
        val selectionArgs = arrayOf("${todo.id}")

        db.update(TodoContract.TodoEntry.TABLE_NAME, values, selection, selectionArgs)
    }
}