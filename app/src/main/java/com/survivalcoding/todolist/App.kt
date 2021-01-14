package com.survivalcoding.todolist

import android.app.Application
import com.survivalcoding.todolist.data.db.TodoDbRepository

class App : Application() {
    val repository = TodoDbRepository(this)
//    val repository = TodoViewModel()
}