package com.survivalcoding.todolist.data

import com.survivalcoding.todolist.view.main.model.Todo

interface DefaultTodoRepository {
    fun getOrderedItems(): List<Todo>

    fun addTodo(item: Todo)

    fun updateTodo(item: Todo)

    fun removeTodo(item: Todo)
}