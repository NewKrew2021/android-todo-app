package com.survivalcoding.todolist.model

import com.survivalcoding.todolist.util.getCurrentTime
import com.survivalcoding.todolist.view.TodoFragment
import java.util.*

class LocalRepository {
    private val _todoList = mutableListOf<TodoItem>()
    var itemId = 0
    val todoList: List<TodoItem>
        get() = _todoList

    fun clearTodoList() {
        _todoList.clear()
    }

    fun addTodo(todoItem: TodoItem) {
        // id 값이 NEW_TODO_TASK 인 경우 새로 id 값 할당
        if (todoItem.id == TodoFragment.NEW_TODO_TASK) {
            todoItem.id = itemId++
        }
        _todoList.add(todoItem)
    }

    fun checkTodo(todoItem: TodoItem, isChecked: Boolean) {
        todoItem.isChecked = isChecked
    }

    fun updateTodo(todoItem: TodoItem, newTodoTitle: String) {
        todoItem.todoTitle = newTodoTitle
        todoItem.timeStamp = getCurrentTime()
    }

    fun removeTodo(todoItem: TodoItem) {
        _todoList.remove(todoItem)
    }

    fun getTodoListSortedByTime(): List<TodoItem> {
        return _todoList.sortedWith(compareBy<TodoItem> { it.isChecked }.thenByDescending { it.timeStamp })
    }

    fun getTodoListSortedByTitle(): List<TodoItem> {
        return _todoList.sortedWith(compareBy<TodoItem> { it.isChecked }.thenBy { it.todoTitle })
    }

    fun searchTodoItem(inputTitle: String): List<TodoItem> {
        return _todoList
            .filter {
                it.todoTitle.toLowerCase(Locale.getDefault())
                    .contains(inputTitle.toLowerCase(Locale.getDefault()))
            }.sortedBy { it.todoTitle.length }
    }
}