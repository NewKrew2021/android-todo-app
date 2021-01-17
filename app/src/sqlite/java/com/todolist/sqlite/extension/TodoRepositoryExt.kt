package com.todolist.sqlite.extension

import android.content.ContentValues
import android.provider.BaseColumns
import com.todolist.sqlite.data.contract.TodoContract
import com.todolist.sqlite.data.model.TodoItem


fun ContentValues.setTodoValues(todoItem: TodoItem) : ContentValues {
    put(TodoContract.TodoEntry.COLUMN_NAME_CONTENTS, todoItem.contents)
    put(TodoContract.TodoEntry.COLUMN_NAME_TIME, todoItem.time)
    put(TodoContract.TodoEntry.COLUMN_NAME_COMPLETE, "${todoItem.complete}")
    return this
}

fun selection() = "${BaseColumns._ID} = ?"

fun selectionArgs(todoItem: TodoItem) = arrayOf("${todoItem.id}")