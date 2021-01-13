package com.survivalcoding.todolist.model.db

import android.provider.BaseColumns

object TodoContract {
    object TodoEntry : BaseColumns {
        const val TABLE_NAME = "todo"
        const val COLUMN_NAME_TITLE = "title"
        const val COLUMN_NAME_IS_CHECKED = "is_checked"
        const val COLUMN_NAME_TIME_STAMP = "time_stamp"
    }
}