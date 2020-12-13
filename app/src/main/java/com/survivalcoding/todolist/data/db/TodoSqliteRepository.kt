package com.survivalcoding.todolist.data.db

import android.content.ContentValues
import android.content.Context
import android.provider.BaseColumns
import com.survivalcoding.todolist.data.DefaultTodoRepository
import com.survivalcoding.todolist.provider.TodoProvider
import com.survivalcoding.todolist.view.main.model.Todo

class TodoSqliteRepository(private val context: Context) : DefaultTodoRepository {

    override fun getOrderedItems(): List<Todo> {
        val projection =
            arrayOf(
                BaseColumns._ID,
                TodoContract.TodoEntry.COLUMN_NAME_TODO,
                TodoContract.TodoEntry.COLUMN_NAME_DATETIME,
                TodoContract.TodoEntry.COLUMN_NAME_IS_DONE,
            )

        val sortOrder = "${TodoContract.TodoEntry.COLUMN_NAME_DATETIME} DESC"
        val cursor = context.contentResolver.query(
            TodoProvider.CONTENT_URI,
            projection, null, null, sortOrder
        )

        val todos = mutableListOf<Todo>()
        cursor?.let {
            with(cursor) {
                while (moveToNext()) {
                    val id = getInt(getColumnIndexOrThrow(BaseColumns._ID))
                    val todo =
                        getString(getColumnIndexOrThrow(TodoContract.TodoEntry.COLUMN_NAME_TODO))
                    val datetime =
                        getLong(getColumnIndexOrThrow(TodoContract.TodoEntry.COLUMN_NAME_DATETIME))
                    val isDone =
                        getInt(getColumnIndexOrThrow(TodoContract.TodoEntry.COLUMN_NAME_IS_DONE))
                    todos.add(Todo(todo, datetime, isDone == 1, id))
                }
                close()
            }
        }

        return todos
    }

    override fun addTodo(item: Todo) {
        val values = ContentValues().apply {
            put(TodoContract.TodoEntry.COLUMN_NAME_TODO, item.todo)
            put(TodoContract.TodoEntry.COLUMN_NAME_DATETIME, item.datetime)
            put(TodoContract.TodoEntry.COLUMN_NAME_IS_DONE, item.isDone)
        }

        context.contentResolver.insert(TodoProvider.CONTENT_URI, values)
    }

    override fun updateTodo(item: Todo) {
        // New value for one column
        val values = ContentValues().apply {
            put(TodoContract.TodoEntry.COLUMN_NAME_TODO, item.todo)
            put(TodoContract.TodoEntry.COLUMN_NAME_DATETIME, item.datetime)
            put(TodoContract.TodoEntry.COLUMN_NAME_IS_DONE, item.isDone)
        }

        // Which row to update, based on the title
        val selection = "${BaseColumns._ID} = ?"
        val selectionArgs = arrayOf("${item.id}")

        context.contentResolver.update(TodoProvider.CONTENT_URI, values, selection, selectionArgs)
    }

    override fun removeTodo(item: Todo) {
        val selection = "${BaseColumns._ID} = ?"
        val selectionArgs = arrayOf("${item.id}")

        context.contentResolver.delete(TodoProvider.CONTENT_URI, selection, selectionArgs)
    }

}