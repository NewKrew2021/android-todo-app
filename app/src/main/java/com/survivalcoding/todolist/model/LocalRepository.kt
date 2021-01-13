package com.survivalcoding.todolist.model

import com.survivalcoding.todolist.util.getCurrentTime
import com.survivalcoding.todolist.view.TodoFragment
import java.util.*

class LocalRepository : TodoRepository{
    private val _todoList = mutableListOf<TodoItem>()
    var itemId = 0
    val todoList: List<TodoItem>
        get() = _todoList

    override fun clearTodoList() {
        _todoList.clear()
    }

    override fun addTodo(todoItem: TodoItem) {
        // id 값이 NEW_TODO_TASK 인 경우 새로 id 값 할당
        if (todoItem.id == TodoFragment.NEW_TODO_TASK) {
            todoItem.id = itemId++
        }
        _todoList.add(todoItem)
    }

    override fun checkTodo(todoItem: TodoItem, isChecked: Boolean) {
        todoItem.isChecked = isChecked
    }

    override fun updateTodo(todoItem: TodoItem, newTodoTitle: String) {
        todoItem.todoTitle = newTodoTitle
        todoItem.timeStamp = getCurrentTime()
    }

    override fun removeTodo(todoItem: TodoItem) {
        _todoList.remove(todoItem)
    }

    override fun getTodoListSortedByTime(): List<TodoItem> {
        return _todoList.sortedWith(compareBy<TodoItem> { it.isChecked }.thenByDescending { it.timeStamp })
    }

    override fun getTodoListSortedByTitle(): List<TodoItem> {
        return _todoList.sortedWith(compareBy<TodoItem> { it.isChecked }.thenBy { it.todoTitle })
    }

    override fun searchTodoItem(inputTitle: String): List<TodoItem> {
        return _todoList
            .filter {
                it.todoTitle.toLowerCase(Locale.getDefault())
                    .contains(inputTitle.toLowerCase(Locale.getDefault()))
            }.sortedBy { it.todoTitle.length }
    }
}