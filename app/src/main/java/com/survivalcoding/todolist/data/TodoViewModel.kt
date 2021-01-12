package com.survivalcoding.todolist.data

import com.survivalcoding.todolist.util.stringToDate
import com.survivalcoding.todolist.view.main.model.Todo
import java.util.concurrent.atomic.AtomicInteger

class TodoViewModel {
    val id = AtomicInteger(0)

    private val _items = mutableListOf<Todo>()
    val items: List<Todo>
        get() = _items

    fun getOrderedItems(): List<Todo> {
        return _items.sortedWith(
            compareBy {
                if (!it.isDone) -stringToDate(it.times).time
                else Long.MAX_VALUE
            }
        )
    }

    fun getOrderedWithFilteredItems(query: String): List<Todo> {
        return _items
            .filter { it.title.contains(query, true) }
            .sortedWith(
                compareBy {
                    if (!it.isDone) -stringToDate(it.times).time
                    else Long.MAX_VALUE
                }
            )
    }

    fun add(todo: Todo) {
        todo.id = id.getAndIncrement()
        _items.add(0, todo)
    }

    fun addAll(newItems: ArrayList<Todo>) {
        _items.clear()
        _items.addAll(newItems)
    }

    fun remove(todo: Todo) {
        _items.remove(todo)
    }

    fun removeAllRemovable(): Boolean {
        val removables = _items.filter { it.isRemovable }

        if (removables.isNotEmpty()) {
            _items.removeAll(removables)
            return true
        }
        return false
    }

    fun getRemovablesCount(): Int = _items.count { it.isRemovable }

    fun clearAllRemovable() {
        _items.forEach { it.isRemovable = false }
    }

    fun edit(todo: Todo): Boolean {
        _items.withIndex().find { it.value.id == todo.id }?.let {
            _items[it.index] = todo
            return true
        }
        return false
    }
}