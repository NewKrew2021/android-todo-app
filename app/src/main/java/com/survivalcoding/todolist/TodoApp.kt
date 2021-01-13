package com.survivalcoding.todolist

import android.app.Application
import com.survivalcoding.todolist.data.repository.TodoRepository

class TodoApp : Application() {
    val todoRepository =
        TodoRepository()
}