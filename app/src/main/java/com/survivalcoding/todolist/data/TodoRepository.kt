package com.survivalcoding.todolist.data

import com.survivalcoding.todolist.view.main.model.TodoData

interface TodoRepository {
    fun getItems(): List<TodoData>

    fun addItem(data: TodoData)

    fun delItem(data: TodoData)

    fun editItem(data: TodoData, changeTodo: String)

    fun doneItem(data: TodoData)
}