package com.survivalcoding.todolist

import android.app.Application
import com.survivalcoding.todolist.repository.database.TodoSQLiteRepository

class App : Application() {
    val todoRepository by lazy {
        TodoSQLiteRepository(applicationContext)
    }
}