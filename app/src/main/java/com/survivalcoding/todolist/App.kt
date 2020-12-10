package com.survivalcoding.todolist

import android.app.Application
import com.survivalcoding.todolist.data.TodoRepository

class App : Application() {
    val todoRepository = TodoRepository()
}