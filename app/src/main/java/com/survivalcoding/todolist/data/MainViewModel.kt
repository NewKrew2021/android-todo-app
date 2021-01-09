package com.survivalcoding.todolist.data

import com.survivalcoding.todolist.util.stringToDate
import com.survivalcoding.todolist.view.main.model.Todo
import java.util.concurrent.atomic.AtomicInteger

class MainViewModel {
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

    fun edit(todo: Todo): Boolean {
        val index = _items.indexOfFirst{ it.id == todo.id }

        return if (index == -1) false
        else {
            _items[index] = todo
            true
        }
    }
}