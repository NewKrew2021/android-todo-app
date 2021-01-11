package com.survivalcoding.todolist.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.survivalcoding.todolist.model.TodoItem

class MainViewModel : ViewModel() {

    private val _todoList = mutableListOf<TodoItem>()
    val todoList: List<TodoItem>
        get() = _todoList
}