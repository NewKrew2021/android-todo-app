package com.survivalcoding.todolist

import android.app.Application
import android.util.Log
import com.survivalcoding.todolist.data.TodoRepository

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Log.d("App", "onCreate: memory")
    }

    val todoRepository by lazy {
        TodoRepository()
    }
}