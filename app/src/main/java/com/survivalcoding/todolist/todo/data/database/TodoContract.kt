package com.survivalcoding.todolist.todo.data.database

import android.provider.BaseColumns

object TodoContract : BaseColumns {
    object TodoEntry : BaseColumns {
        const val TABLE_NAME = "todo"
        const val COLUMN_NAME_IS_DONE = "isDone"
        const val COLUMN_NAME_TITLE = "title"
        const val COLUMN_NAME_DUE_DATE = "dueDate"
        const val COLUMN_NAME_WRITE_TIME = "writeTime"
    }
}