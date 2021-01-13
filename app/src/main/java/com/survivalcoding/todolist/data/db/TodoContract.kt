package com.survivalcoding.todolist.data.db

import android.provider.BaseColumns

object TodoContract {
    object TodoEntry : BaseColumns {
        const val TABLE_NAME = "todo"
        const val COLUMN_NAME_TEXT = "text"
        const val COLUMN_NAME_TIME = "time"
        const val COLUMN_NAME_IS_DONE = "is_done"
    }
}