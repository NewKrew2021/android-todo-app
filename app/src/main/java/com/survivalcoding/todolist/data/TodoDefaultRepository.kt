package com.survivalcoding.todolist.data

import com.survivalcoding.todolist.view.main.model.TodoData

interface TodoDefaultRepository {
    fun getItems(): List<TodoData> {
        return listOf()
    }

    fun addItem(data: TodoData) {
    }

    fun delItem(data: TodoData) {
    }

    fun addAllItems(data: List<TodoData>) {
    }
}