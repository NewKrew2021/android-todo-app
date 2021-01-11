package com.survivalcoding.todolist.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.survivalcoding.todolist.model.TodoItem

class MainViewModel : ViewModel() {

    private var _todoList = mutableListOf<TodoItem>()
    val todoList: List<TodoItem>
        get() = _todoList

    fun addTodoItem(todoItem: TodoItem) {
        _todoList.add(todoItem)
        sortTodoItem()
    }

    fun sortTodoItem() {
        _todoList = _todoList.sortedWith(compareBy(
            { if (it.complete) 1 else 0 },
            { -it.time }
        )).toMutableList()
    }
}