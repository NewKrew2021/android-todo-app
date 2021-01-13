package com.survivalcoding.todolist.data.repository

import android.content.ContentValues
import android.content.Context
import android.provider.BaseColumns
import com.survivalcoding.todolist.data.contract.TodoContract
import com.survivalcoding.todolist.data.db.TodoDbHelper
import com.survivalcoding.todolist.data.model.TodoItem


class TodoRepoImpl(context: Context) : TodoRepo {

    private val dbHelper = TodoDbHelper(context)

    override fun getAllTodoItem(): MutableList<TodoItem> {
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
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(TodoContract.TodoEntry.COLUMN_NAME_CONTENTS, todoItem.contents)
            put(TodoContract.TodoEntry.COLUMN_NAME_TIME, todoItem.time)
            put(TodoContract.TodoEntry.COLUMN_NAME_COMPLETE, "${todoItem.complete}")
        }

        db.insert(TodoContract.TodoEntry.TABLE_NAME, null, values)
    }

    override fun updateTodo(todoItem: TodoItem) {
        val db = dbHelper.writableDatabase

        val values = ContentValues().apply {
            put(TodoContract.TodoEntry.COLUMN_NAME_CONTENTS, todoItem.contents)
            put(TodoContract.TodoEntry.COLUMN_NAME_TIME, todoItem.time)
            put(TodoContract.TodoEntry.COLUMN_NAME_COMPLETE, "${todoItem.complete}")
        }

        val selection = "${BaseColumns._ID} = ?"
        val selectionArgs = arrayOf("${todoItem.id}")

        db.update(
            TodoContract.TodoEntry.TABLE_NAME,
            values,
            selection,
            selectionArgs
        )
    }

    override fun removeTodo(todoItem: TodoItem) {
        val db = dbHelper.writableDatabase

        val selection = "${BaseColumns._ID} = ?"
        val selectionArgs = arrayOf("${todoItem.id}")

        db.delete(TodoContract.TodoEntry.TABLE_NAME, selection, selectionArgs)
    }
}