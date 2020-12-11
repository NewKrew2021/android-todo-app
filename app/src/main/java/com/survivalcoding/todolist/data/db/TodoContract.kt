package com.survivalcoding.todolist.data.db

import android.provider.BaseColumns

object TodoContract {
    object TodoEntry : BaseColumns {
        const val TABLE_NAME = "todo"
        const val COLUMN_NAME_TODO = "title"
        const val COLUMN_NAME_DATETIME = "datetime"
        const val COLUMN_NAME_IS_DONE = "is_done"
    }
}