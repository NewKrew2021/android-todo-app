package com.survivalcoding.todolist

import android.app.Application
import com.survivalcoding.todolist.todo.data.database.TodoSQLiteData

class TodoApp : Application() {
    override fun onCreate() {
        super.onCreate()
    }

    val todoDataModel by lazy {
        TodoSQLiteData(applicationContext)
    }
}