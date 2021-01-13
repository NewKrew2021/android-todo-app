package com.survivalcoding.todolist.data

import com.survivalcoding.todolist.view.main.model.Todo

interface DefaultTodoRepository {

    fun getOrderedItems(): List<Todo>

    fun getOrderedWithFilteredItems(query: String): List<Todo>

    fun add(todo: Todo)

    fun remove(todo: Todo)

    fun removeAllRemovable()

    fun getRemovablesCount(): Int

    fun update(todo: Todo)
}