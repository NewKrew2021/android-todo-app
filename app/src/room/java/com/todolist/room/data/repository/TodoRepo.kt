package com.todolist.room.data.repository

import com.todolist.room.data.model.TodoItem

interface TodoRepo {

    fun getAllTodoItem(): List<TodoItem>

    fun addTodo(todoItem: TodoItem)

    fun updateTodo(todoItem: TodoItem)

    fun removeTodo(todoItem: TodoItem)

}