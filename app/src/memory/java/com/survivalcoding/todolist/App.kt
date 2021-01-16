package com.survivalcoding.todolist

import android.app.Application
import com.survivalcoding.todolist.data.TodoLocalRepository

class App : Application() {
    val todoRepository = TodoLocalRepository()
}