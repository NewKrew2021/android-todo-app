package com.survivalcoding.todolist

import android.app.Application
import com.survivalcoding.todolist.data.db.TodoSqliteRepository

class App : Application() {
    val repository = TodoSqliteRepository(this)
//    val repository = TodoViewModel()
}