package com.survivalcoding.todolist.viewmodel

import androidx.lifecycle.ViewModel
import com.survivalcoding.todolist.model.TodoItem

class TodoViewModel : ViewModel() {

    var items = mutableListOf<TodoItem>()
    fun getItemList() = items

    fun add(item: TodoItem) {
        items.add(item)
    }

    fun remove(item: TodoItem){
        items.remove(item)
    }

    fun modify(item: TodoItem) {
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

    fun sort() = items.sortedWith(compareBy<TodoItem>(
            { it.isComplete },
            { it.isMark },
            { it.date }))

    fun complete(item: TodoItem) {
        items.filter { it.id == item.id }.forEach {
            it.isComplete = !it.isComplete
        }
    }

    fun mark(item: TodoItem) {
        items.filter { it.id == item.id }.forEach {
            it.isMark = !it.isMark
        }
    }
}