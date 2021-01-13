package com.todolist.sqlite.data.repository

import com.todolist.sqlite.data.model.TodoItem

interface TodoRepo {

    fun getAllTodoItem(): List<TodoItem>

    fun addTodo(todoItem: TodoItem)

    fun updateTodo(todoItem: TodoItem)

    fun removeTodo(todoItem: TodoItem)

}