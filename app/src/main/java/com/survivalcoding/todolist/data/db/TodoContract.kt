package com.survivalcoding.todolist.data.db

import android.provider.BaseColumns

object TodoContract {
    object TodoEntry : BaseColumns {
        const val TABLE_NAME = "todo_list"
        const val COLUMN_NAME_TITLE = "title"
        const val COLUMN_NAME_TIMES = "times"
        const val COLUMN_NAME_IS_DONE = "is_done"
        const val COLUMN_NAME_IS_OPTION = "is_option"
        const val COLUMN_NAME_IS_REMOVABLE = "is_removable"
    }
}