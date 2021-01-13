package com.survivalcoding.todolist.data.contract

import android.provider.BaseColumns

object TodoContract {
    object TodoEntry : BaseColumns {
        const val TABLE_NAME = "todo"
        const val COLUMN_NAME_TODO = "title"
        const val COLUMN_NAME_TIME = "time"
        const val COLUMN_NAME_COMPLETE = "complete"
    }
}