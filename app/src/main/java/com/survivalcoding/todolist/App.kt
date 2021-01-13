package com.survivalcoding.todolist

import android.app.Application
import com.survivalcoding.todolist.data.db.TodoSqliteRepository

class App : Application() {
    //    val todoRepository = TodoLocalRepository()
    val todoRepository by lazy {
        TodoSqliteRepository(applicationContext)
    }
}