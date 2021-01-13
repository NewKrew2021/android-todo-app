package com.todolist.room.data.repository

import android.content.ContentValues
import android.content.Context
import android.provider.BaseColumns
import com.todolist.room.data.contract.TodoContract
import com.todolist.room.data.db.TodoDbHelper
import com.todolist.room.data.model.TodoItem
import com.todolist.room.extension.selection
import com.todolist.room.extension.selectionArgs
import com.todolist.room.extension.setTodoValues


class TodoRepoImpl(context: Context) : TodoRepo {

    private val dbHelper = TodoDbHelper(context)

    override fun getAllTodoItem(): List<TodoItem> {
        val db = dbHelper.readableDatabase

        val projection =
            arrayOf(
                BaseColumns._ID,
                TodoContract.TodoEntry.COLUMN_NAME_CONTENTS,
                TodoContract.TodoEntry.COLUMN_NAME_TIME,
                TodoContract.TodoEntry.COLUMN_NAME_COMPLETE,
            )

        val cursor = db.query(
            TodoContract.TodoEntry.TABLE_NAME,
            projection,
            null,
            null,
            null,
            null,
            null,
        )

        var todoList = mutableListOf<TodoItem>()

        with(cursor) {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(BaseColumns._ID))
                val contents =
                    getString(getColumnIndexOrThrow(TodoContract.TodoEntry.COLUMN_NAME_CONTENTS))
                val time = getLong(getColumnIndexOrThrow(TodoContract.TodoEntry.COLUMN_NAME_TIME))
                val complete =
                    getString(getColumnIndexOrThrow(TodoContract.TodoEntry.COLUMN_NAME_COMPLETE)).let {
                        it == "true"
                    }
                todoList.add(TodoItem(time, contents, complete, id))
            }
            close()
        }

        todoList = todoList.sortedWith(compareBy(
            { if (it.complete) 1 else 0 },
            { -it.time }
        )).toMutableList()

        return todoList
    }

    override fun addTodo(todoItem: TodoItem) {
        dbHelper.writableDatabase.run {
            insert(
                TodoContract.TodoEntry.TABLE_NAME,
                null,
                ContentValues().setTodoValues(todoItem)
            )
        }
    }

    override fun updateTodo(todoItem: TodoItem) {
        dbHelper.writableDatabase.run {
            update(
                TodoContract.TodoEntry.TABLE_NAME,
                ContentValues().setTodoValues(todoItem),
                selection(),
                selectionArgs(todoItem)
            )
        }
    }

    override fun removeTodo(todoItem: TodoItem) {
        dbHelper.writableDatabase.run {
            delete(
                TodoContract.TodoEntry.TABLE_NAME,
                selection(),
                selectionArgs(todoItem)
            )
        }
    }
}