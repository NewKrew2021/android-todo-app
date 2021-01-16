package com.survivalcoding.todolist

import android.app.Application
import com.survivalcoding.todolist.repository.memory.TodoMemoryRepository

class App : Application() {
    val todoRepository by lazy {
        TodoMemoryRepository()
    }
}