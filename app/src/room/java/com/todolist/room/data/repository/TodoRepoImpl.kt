package com.todolist.room.data.repository

import com.todolist.room.data.db.TodoDao
import com.todolist.room.data.model.TodoItem


class TodoRepoImpl(private val todoDao: TodoDao) : TodoRepo {

    override fun getAllTodoItem(): List<TodoItem>  = todoDao.getAllTodoItem()

    override fun addTodo(todoItem: TodoItem) = todoDao.addTodo(todoItem)

    override fun updateTodo(todoItem: TodoItem) = todoDao.updateTodo(todoItem)

    override fun removeTodo(todoItem: TodoItem) = todoDao.removeTodo(todoItem.id)
}