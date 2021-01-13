package com.survivalcoding.todolist.data.repository

import com.survivalcoding.todolist.data.model.TodoItem

interface TodoRepo {
    fun getAllTodoItem(): List<TodoItem>

    fun addTodo(todoItem: TodoItem)

    fun updateTodo(todoItem: TodoItem)

    fun removeTodo(todoItem: TodoItem)


}