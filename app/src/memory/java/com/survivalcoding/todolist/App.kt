package com.survivalcoding.todolist

import android.app.Application
import com.survivalcoding.todolist.data.TodoViewModel

class App : Application() {
    val repository = TodoViewModel()
}