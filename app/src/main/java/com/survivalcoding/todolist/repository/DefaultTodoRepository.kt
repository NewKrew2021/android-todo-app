package com.survivalcoding.todolist.repository

import com.survivalcoding.todolist.model.TodoItem
import com.survivalcoding.todolist.repository.database.MyCallback

interface DefaultTodoRepository {
    fun getOrderedItems(callback: MyCallback)

    fun add(item : TodoItem)

    fun remove(item : TodoItem)

    fun update(item: TodoItem)

}