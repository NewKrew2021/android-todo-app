package com.survivalcoding.todolist.data

import com.survivalcoding.todolist.view.main.model.Todo
import java.util.concurrent.atomic.AtomicInteger

class TodoRepository : DefaultTodoRepository {
    var id = AtomicInteger(0)
    var items = mutableListOf<Todo>()

    override fun getOrderedItems() = items.sortedByDescending { it.datetime }

    override fun addTodo(item: Todo) {
        item.id = id.getAndIncrement()
        items.add(item)
    }

    override fun updateTodo(item: Todo) {
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

    override fun removeTodo(item: Todo) {
        items.remove(item)
    }

}