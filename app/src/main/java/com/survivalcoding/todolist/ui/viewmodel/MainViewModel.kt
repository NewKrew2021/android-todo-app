package com.survivalcoding.todolist.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.survivalcoding.todolist.data.model.TodoItem
import com.survivalcoding.todolist.data.repository.TodoRepoImpl

class MainViewModel(private val todoRepository: TodoRepoImpl) : ViewModel() {

    private var _todoList = mutableListOf<TodoItem>()
    val todoList: List<TodoItem>
        get() = _todoList

    fun getTodoList() {
        _todoList = todoRepository.getAllTodoItem()
    }

    fun addTodoItem(todoItem: TodoItem) {
        todoRepository.addTodo(todoItem)
    }

    fun removeTodoItem(todoItem: TodoItem) {
        todoRepository.removeTodo(todoItem)
    }

    fun updateTodoItem(todoItem: TodoItem) {
        todoRepository.updateTodo(todoItem)
    }

}