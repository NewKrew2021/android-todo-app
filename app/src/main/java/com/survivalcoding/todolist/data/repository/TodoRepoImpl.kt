package com.survivalcoding.todolist.data.repository

import android.content.Context
import com.survivalcoding.todolist.data.db.TodoDbHelper
import com.survivalcoding.todolist.data.model.TodoItem


class TodoRepoImpl(context: Context) : TodoRepo {

    private val dbHelper = TodoDbHelper(context)

    override fun getAllTodoItem(): List<TodoItem> {
        TODO("Not yet implemented")
    }

    override fun addTodo(todoItem: TodoItem) {
        TODO("Not yet implemented")
    }

    override fun updateTodo(todoItem: TodoItem) {
        TODO("Not yet implemented")
    }

    override fun removeTodo(todoItem: TodoItem) {
        TODO("Not yet implemented")
    }
}