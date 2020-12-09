package com.survivalcoding.todolist.view.main

import com.survivalcoding.todolist.view.main.model.Todo
import java.util.concurrent.atomic.AtomicInteger

class TodoViewModel {
    var id = AtomicInteger(0)
    var items = mutableListOf<Todo>()

    fun addTodo(item: Todo) {
        item.id = id.getAndIncrement()
        items.add(item)
    }

    fun updateTodo(item: Todo) {
        val changeData = items.map { e ->
            if (e.id == item.id) {
                item
            } else {
                e
            }
        }

        items.apply {
            clear()
            addAll(changeData)
        }
    }

    fun removeTodo(item: Todo) {
        items.remove(item)
    }

}