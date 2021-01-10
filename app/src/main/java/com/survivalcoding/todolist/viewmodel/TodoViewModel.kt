package com.survivalcoding.todolist.viewmodel

import com.survivalcoding.todolist.model.TodoItem

class TodoViewModel {

    var items = mutableListOf<TodoItem>()

    fun getItemList() = items

    fun add(item: TodoItem) = items.add(item)

    fun remove(item: TodoItem) = items.remove(item)
}