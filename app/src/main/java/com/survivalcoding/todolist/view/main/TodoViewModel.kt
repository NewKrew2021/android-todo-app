package com.survivalcoding.todolist.view.main

import com.survivalcoding.todolist.view.main.model.Todo
import java.util.concurrent.atomic.AtomicInteger

class TodoViewModel {
    private var _id = AtomicInteger(0)

    private val _items = mutableListOf<Todo>()

    val items: List<Todo>
        get() = _items.sortedByDescending { it.datetime }

    fun addTodo(item: Todo) {
        item.id = _id.getAndIncrement()
        _items.add(item)
    }

    fun updateTodo(item: Todo) {
        val changeData = _items.map { e ->
            if (e.id == item.id) {
                item
            } else {
                e
            }
        }

        _items.apply {
            clear()
            addAll(changeData)
        }
    }

    fun removeTodo(item: Todo) {
        _items.remove(item)
    }

}