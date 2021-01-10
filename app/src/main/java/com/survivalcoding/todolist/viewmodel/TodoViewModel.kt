package com.survivalcoding.todolist.viewmodel

import com.survivalcoding.todolist.model.TodoItem
import com.survivalcoding.todolist.util.getCurrentTime
import com.survivalcoding.todolist.view.MainActivity

class TodoViewModel {
    private val _todoList = mutableListOf<TodoItem>()
    var itemId = 0
    val todoList: List<TodoItem>
        get() = _todoList

    fun clearTodoList() {
        _todoList.clear()
    }

    fun addTodo(todoItem: TodoItem) {
        // id 값이 NEW_TODO_TASK 인 경우 새로 id 값 할당
        if (todoItem.id == MainActivity.NEW_TODO_TASK) {
            todoItem.id = itemId++
        }
        _todoList.add(todoItem)
    }

    fun editTodo(todoItem: TodoItem, newTodoTitle: String) {
        todoItem.todoTitle = newTodoTitle
        todoItem.timeStamp = getCurrentTime()
    }

    fun removeTodo(todoItem: TodoItem) {
        _todoList.remove(todoItem)
    }

    fun sortByTime() {
        _todoList.sortWith(compareBy<TodoItem> { it.isChecked }.thenByDescending { it.timeStamp })
    }

    fun sortByTitle() {
        _todoList.sortWith(compareBy<TodoItem> { it.isChecked }.thenBy { it.todoTitle })
    }
}