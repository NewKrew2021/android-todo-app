package com.survivalcoding.todolist.extension

import android.content.ContentValues
import android.provider.BaseColumns
import com.survivalcoding.todolist.data.contract.TodoContract
import com.survivalcoding.todolist.data.model.TodoItem


fun ContentValues.setValues(todoItem: TodoItem) : ContentValues {
    put(TodoContract.TodoEntry.COLUMN_NAME_CONTENTS, todoItem.contents)
    put(TodoContract.TodoEntry.COLUMN_NAME_TIME, todoItem.time)
    put(TodoContract.TodoEntry.COLUMN_NAME_COMPLETE, "${todoItem.complete}")
    return this
}

fun selection() = "${BaseColumns._ID} = ?"

fun selectionArgs(todoItem: TodoItem) = arrayOf("${todoItem.id}")
