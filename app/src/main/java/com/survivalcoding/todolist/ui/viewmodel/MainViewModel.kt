package com.survivalcoding.todolist.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.survivalcoding.todolist.data.model.TodoItem
import com.survivalcoding.todolist.data.repository.TodoRepoImpl

class MainViewModel(val todoRepository: TodoRepoImpl) : ViewModel() {

    private var _todoList = mutableListOf<TodoItem>()
    val todoList: List<TodoItem>
        get() = _todoList

    fun clearAllTodoItem() {
        _todoList.clear()
    }

    fun addAllTodoItem(list: ArrayList<TodoItem>) {
        _todoList.addAll(list)
    }

    fun addTodoItem(todoItem: TodoItem) {
        _todoList.add(todoItem)
        sortTodoItem()
    }

    fun setTodoList(list: ArrayList<TodoItem>) {
        _todoList = list
    }

    fun sortTodoItem() {
        _todoList = _todoList.sortedWith(compareBy(
            { if (it.complete) 1 else 0 },
            { -it.time }
        )).toMutableList()
    }

}