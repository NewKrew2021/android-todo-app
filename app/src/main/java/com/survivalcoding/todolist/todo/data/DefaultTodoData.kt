package com.survivalcoding.todolist.todo.data

import com.survivalcoding.todolist.todo.view.OrderMethod
import com.survivalcoding.todolist.todo.view.SortingBase
import com.survivalcoding.todolist.todo.view.model.Todo

interface DefaultTodoData {
    fun addTodo(item: Todo)
    fun deleteTodo(item: Todo)
    fun updateTodo(item: Todo)
    fun sorting(
        sortingBase: SortingBase,
        orderMethod: OrderMethod,
        updateUI: (List<Todo>) -> Unit
    )

    fun search(title: String, updateUI: (List<Todo>) -> Unit)
}