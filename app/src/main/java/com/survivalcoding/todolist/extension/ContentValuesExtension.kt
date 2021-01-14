package com.survivalcoding.todolist.extension

import android.content.ContentValues
import com.survivalcoding.todolist.model.TodoItem
import com.survivalcoding.todolist.repository.database.TodoContract

fun ContentValues.setData(item:TodoItem) : ContentValues{
    return this.apply {
        put(TodoContract.TodoEntry.COLUMN_NAME_TITLE, item.title)
        put(TodoContract.TodoEntry.COLUMN_NAME_DATE, item.date)
        put(TodoContract.TodoEntry.COLUMN_NAME_DONE, item.isComplete)
        put(TodoContract.TodoEntry.COLUMN_NAME_MARK, item.isMark)
    }
}