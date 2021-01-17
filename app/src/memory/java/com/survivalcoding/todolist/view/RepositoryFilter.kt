package com.survivalcoding.todolist.view

import android.content.Context
import com.survivalcoding.todolist.data.DefaultTodoRepository
import com.survivalcoding.todolist.data.TodoRepository
import com.survivalcoding.todolist.data.db.TodoSqliteRepository

class RepositoryFilter {

    fun getRepository(context: Context): DefaultTodoRepository {
        return TodoRepository()
    }
}