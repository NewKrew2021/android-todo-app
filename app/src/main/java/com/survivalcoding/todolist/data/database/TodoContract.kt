package com.survivalcoding.todolist.data.database

import android.provider.BaseColumns

object TodoContract {
    object TodoEntry : BaseColumns {
        const val TABLE_NAME = "todo"
        const val COLUMN_NAME_TITLE = "title"
        const val COLUMN_NAME_TIMESTAMP = "timestamp"
        const val COLUMN_NAME_IS_CHECKED = "is_checked"
        const val COLUMN_NAME_CONTENT = "content"
    }
}