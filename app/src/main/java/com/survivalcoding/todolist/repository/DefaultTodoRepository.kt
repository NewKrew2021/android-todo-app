package com.survivalcoding.todolist.repository

import com.survivalcoding.todolist.model.TodoItem

interface DefaultTodoRepository {
    fun getOrderedItems() : List<TodoItem>

    fun add(item : TodoItem)

    fun remove(item : TodoItem)

    fun update(item: TodoItem)

}