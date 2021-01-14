package com.survivalcoding.todolist.data

import com.survivalcoding.todolist.model.TodoItem

interface DefaultTodoRepository {

    fun addItem(item: TodoItem)
    fun removeItem(targetItem: TodoItem)
    fun updateItem(item: TodoItem)
    fun getOrderedItems(): List<TodoItem>
    fun getFilteredItemsBy(keyword: String): List<TodoItem>
}