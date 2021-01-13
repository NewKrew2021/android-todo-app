package com.survivalcoding.todolist.model

interface TodoRepository {
    fun addTodo(todoItem: TodoItem)

    fun checkTodo(todoItem: TodoItem, isChecked: Boolean)

    fun updateTodo(todoItem: TodoItem, newTodoTitle: String)

    fun removeTodo(todoItem: TodoItem)

    fun getTodoListSortedByTime(): List<TodoItem>

    fun getTodoListSortedByTitle(): List<TodoItem>

    fun searchTodoItem(inputTitle: String): List<TodoItem>

    fun clearTodoList()
}