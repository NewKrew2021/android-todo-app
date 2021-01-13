package com.survivalcoding.todolist.repository.database

import android.provider.BaseColumns

object TodoContract {
    object TodoEntry : BaseColumns {
        const val TABLE_NAME = "todo"
        const val COLUMN_NAME_TITLE = "title"
        const val COLUMN_NAME_DATE = "date"
        const val COLUMN_NAME_DONE = "complete"
        const val COLUMN_NAME_MARK = "bookmark"
    }
}