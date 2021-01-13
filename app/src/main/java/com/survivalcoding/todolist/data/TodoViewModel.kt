package com.survivalcoding.todolist.data

import com.survivalcoding.todolist.view.main.model.Todo
import java.util.concurrent.atomic.AtomicInteger

class TodoViewModel : DefaultTodoRepository {
    val id = AtomicInteger(0)

    private val items = mutableListOf<Todo>()

    override fun getOrderedItems(): List<Todo> {
        return items.sortedWith(
            compareBy(
                { it.isDone },
                { -it.times },
            )
        )
    }

    override fun getOrderedWithFilteredItems(query: String): List<Todo> {
        return items
            .filter { it.title.contains(query, true) }
            .sortedWith(
                compareBy(
                    { it.isDone },
                    { -it.times },
                )
            )
    }

    override fun add(todo: Todo) {
        todo.id = id.getAndIncrement()
        items.add(0, todo)
    }

    override fun remove(todo: Todo) {
        items.remove(todo)
    }

    override fun removeAllRemovable() {
        items.removeAll(items.filter { it.isRemovable })
    }

    override fun getRemovablesCount(): Int = items.count { it.isRemovable }

    override fun update(todo: Todo) {
        items.withIndex().find { it.value.id == todo.id }?.let {
            items[it.index] = todo
        }
    }
}