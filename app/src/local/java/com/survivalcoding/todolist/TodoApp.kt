package com.survivalcoding.todolist

import android.app.Application
import com.survivalcoding.todolist.todo.data.TodoData

class TodoApp : Application() {
    override fun onCreate() {
        super.onCreate()
    }

    val todoDataModel by lazy {
        TodoData()
    }
}