package com.survivalcoding.todolist

import android.app.Application
import com.survivalcoding.todolist.data.database.TodoSqliteRepository

class TodoApp : Application() {
    val todoRepository by lazy {
        TodoSqliteRepository(applicationContext)
    }
}