package com.survivalcoding.todolist.viewmodel

import com.survivalcoding.todolist.model.TodoItem
import java.util.*

class MainViewModel {

    private val _list = mutableListOf<TodoItem>()
    val list: List<TodoItem> = _list

    fun addItem(title: String) {
        _list.add(0,
            TodoItem(
                title,
                false,
                Calendar.getInstance().timeInMillis
            )
        )
    }

    fun resetItems(items: MutableList<TodoItem>) {
        _list.clear()
        _list.addAll(items)
    }

    fun removeItem(targetPosition: Int) {
        targetPosition
            .takeIf { it in 0 until _list.size }
            ?.let {
                _list.removeAt(it)
            }
    }

    fun sortItems() {
        _list.sortWith(compareBy({ it.checked }, { -it.timeStamp }))
    }

}