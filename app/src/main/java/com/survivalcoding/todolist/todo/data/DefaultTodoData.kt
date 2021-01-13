package com.survivalcoding.todolist.todo.data

import com.survivalcoding.todolist.todo.view.model.Todo

interface DefaultTodoData {
    fun addTodo(item: Todo)
    fun deleteTodo(item: Todo)
    fun updateTodo(item: Todo)
    fun sorting(sortingBase: Int, orderMethod: Int): MutableList<Todo>
}