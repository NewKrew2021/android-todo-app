package com.survivalcoding.todolist.data.db

import android.provider.BaseColumns

object TodoContract {
    // Table contents are grouped together in an anonymous object.
    object TodoEntry : BaseColumns {
        const val TABLE_NAME = "todoList"
        const val COLUMN_NAME_TODO = "todo"
        const val COLUMN_NAME_TIME = "time"
        const val COLUMN_NAME_CHECKING = "checking"
        const val COLUMN_NAME_COMPLETE = "complete"
    }
}