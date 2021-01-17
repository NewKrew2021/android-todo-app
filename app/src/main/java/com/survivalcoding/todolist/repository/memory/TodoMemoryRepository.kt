package com.survivalcoding.todolist.repository.memory

import com.survivalcoding.todolist.model.TodoItem
import com.survivalcoding.todolist.repository.DefaultTodoRepository
import com.survivalcoding.todolist.repository.database.MyCallback

class TodoMemoryRepository : DefaultTodoRepository {

    var items = mutableListOf<TodoItem>()

    fun sort() = items.sortedWith(compareBy<TodoItem>(
        { it.isComplete },
        { it.isMark },
        { it.date }))

    override fun getOrderedItems(callback: MyCallback) {
        callback.getListCallBack(sort())
    }

    override fun add(item: TodoItem) {
        item.id = DataId.id
        DataId.id += 1
        items.add(item)
    }

    override fun remove(item: TodoItem) {
        items.remove(item)
    }

    override fun update(item: TodoItem) {
        val modifyItems = items.map {
            if (it.id == item.id) {
                item
            } else {
                it
            }
        }
        items.apply {
            clear()
            addAll(modifyItems)
        }
    }
}

object DataId{
    var id: Int = 0
}