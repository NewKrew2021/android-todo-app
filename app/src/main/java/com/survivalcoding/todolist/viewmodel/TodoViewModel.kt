package com.survivalcoding.todolist.viewmodel

import com.survivalcoding.todolist.model.TodoItem

class TodoViewModel {
    private val _todoList = mutableListOf<TodoItem>()
    var itemId = 0
    val todoList: List<TodoItem>
        get() = _todoList

    fun clearTodoList() {
        _todoList.clear()
    }

    fun addTodo(todoItem: TodoItem) {
        if (todoItem.id == null) {
            todoItem.id = itemId++
        }
        _todoList.add(todoItem)
    }

    fun removeTodo(todoItem: TodoItem) {
        _todoList.remove(todoItem)
    }

    fun sortByTime() {
        _todoList.sortBy { it.timeStamp }
    }

    fun sortByTitle() {
        _todoList.sortBy { it.todoTitle }
    }
}