package com.survivalcoding.todolist.data.db

import android.content.Context
import com.survivalcoding.todolist.data.DefaultTodoRepository

class TodoSqliteRepository(context: Context) : DefaultTodoRepository {

    val dbHelper = TodoDbHelper(context)


}