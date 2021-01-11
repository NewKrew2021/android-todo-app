package com.survivalcoding.todolist.viewmodel

import com.survivalcoding.todolist.model.TodoItem

class MainViewModel {

    private val _list = mutableListOf<TodoItem>()
    val list: List<TodoItem> = _list

    fun addItem(item: TodoItem) {
        _list.add(0, item)
    }

    fun resetItems(items: MutableList<TodoItem>) {
        _list.clear()
        _list.addAll(items)
    }

    fun removeItem(targetItem: TodoItem) {
        _list.takeIf { it.contains(targetItem) }?.remove(targetItem)
    }

    fun sortItems() {
        _list.sortWith(compareBy({ it.checked }, { -it.timeStamp }))
    }

    fun getFilteredItems(keyword: String): List<TodoItem> =
        if (keyword.isNotBlank()) _list.filter { it.title.contains(keyword) }.map { it } else list
}