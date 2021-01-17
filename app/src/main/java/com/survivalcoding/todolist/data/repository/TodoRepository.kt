package com.survivalcoding.todolist.data.repository

import com.survivalcoding.todolist.data.DefaultTodoRepository
import com.survivalcoding.todolist.model.TodoItem
import java.util.*
import java.util.concurrent.atomic.AtomicInteger

class TodoRepository : DefaultTodoRepository {

    var id = AtomicInteger(0)

    private var _list = mutableListOf<TodoItem>()
    val list: List<TodoItem> = _list

    override fun addItem(item: TodoItem) {
        _list.add(0, item.apply { id = this@TodoRepository.id.getAndIncrement() })
    }

    override fun removeItem(targetItem: TodoItem) {
        _list.takeIf { it.contains(targetItem) }?.remove(targetItem)
    }

    override fun updateItem(item: TodoItem) {
        var targetIdx = 0
        _list.forEachIndexed { index, todoItem ->
            if (todoItem.id == item.id) targetIdx = index
        }

        _list.removeAt(targetIdx)
        _list.add(item)
    }

    override fun getOrderedItems(): List<TodoItem> {
        return list.sortedWith(compareBy({ it.isChecked }, { -it.timeStamp }))
    }

    override fun getFilteredItemsBy(keyword: String): List<TodoItem> {
        return if (keyword.isNotBlank()) _list.filter {
            it.title.toLowerCase(Locale.ROOT).contains(
                keyword.toLowerCase(
                    Locale.ROOT
                )
            )
        }
            .map { it } else list
    }
}