package com.survivalcoding.todolist.viewmodel

import com.survivalcoding.todolist.model.TodoItem

class TodoViewModel {

    var items = mutableListOf<TodoItem>()

    fun getItemList() = items.toMutableList()

    fun add(item: TodoItem) = items.add(item)

    fun remove(item: TodoItem) = items.remove(item)

    fun modify(item: TodoItem) {
        val modifyItems = items.map {
            if (it.id == item.id) {
                item
            } else {
                it
            }
        }
        items.apply {
            clear()
            addAll(modifyItems)
        }
    }
}