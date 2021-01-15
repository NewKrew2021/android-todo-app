package com.survivalcoding.todolist.data.repository

import com.survivalcoding.todolist.data.DefaultTodoRepository
import com.survivalcoding.todolist.model.TodoItem
import java.util.concurrent.atomic.AtomicInteger

class TodoRepository : DefaultTodoRepository {

    var id = AtomicInteger(0)

    private val _list = mutableListOf<TodoItem>()
    val list: List<TodoItem> = _list

    override fun addItem(item: TodoItem) {
        _list.add(0, item.apply { id = this@TodoRepository.id.getAndIncrement() })
    }

    override fun removeItem(targetItem: TodoItem) {
        _list.takeIf { it.contains(targetItem) }?.remove(targetItem)
    }

    override fun updateItem(item: TodoItem) {
        _list.forEach {
            if (it.id == item.id) _list.remove(it)
        }
        _list.add(item)
    }

    override fun getOrderedItems(): List<TodoItem> {
        return list.sortedWith(compareBy({ it.isChecked }, { -it.timeStamp }))
    }

    override fun getFilteredItemsBy(keyword: String): List<TodoItem> {
        return if (keyword.isNotBlank()) _list.filter { it.title.contains(keyword) }
            .map { it } else list
    }
}