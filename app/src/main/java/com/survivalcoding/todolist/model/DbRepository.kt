package com.survivalcoding.todolist.model

import android.content.Context
import com.survivalcoding.todolist.model.db.asynctask.*
import com.survivalcoding.todolist.model.db.TodoContract
import com.survivalcoding.todolist.model.db.TodoDbHelper

class DbRepository(context: Context) : TodoRepository {
    private val dbHelper = TodoDbHelper(context)
    private val db = dbHelper.writableDatabase

    override fun addTodo(todoItem: TodoItem) {
        AddTodoAsyncTask(db).execute(todoItem)
    }

    private fun getTodoItems(): List<TodoItem> {
        return ReadTodoListAsyncTask(db).execute().get()
    }

    override fun checkTodo(todoItem: TodoItem, isChecked: Boolean) {
        CheckTodoAsyncTask(db).execute(CheckTodoParam(todoItem, isChecked))
    }

    override fun updateTodo(todoItem: TodoItem, newTodoTitle: String) {
        EditTodoAsyncTask(db).execute(EditTodoParam(todoItem, newTodoTitle))
    }

    override fun removeTodo(todoItem: TodoItem) {
        RemoveTodoAsyncTask(db).execute(todoItem)
    }

    override fun getTodoListSortedByTime(): List<TodoItem> {
        return getTodoItems().sortedWith(compareBy<TodoItem> { it.isChecked }.thenByDescending { it.timeStamp })
    }

    override fun getTodoListSortedByTitle(): List<TodoItem> {
        return getTodoItems().sortedWith(compareBy<TodoItem> { it.isChecked }.thenBy { it.todoTitle })
    }

    override fun searchTodoItem(inputTitle: String): List<TodoItem> {
        return SearchTodoAsyncTask(db).execute(inputTitle).get()
    }

    override fun clearTodoList() {
        db.delete(TodoContract.TodoEntry.TABLE_NAME, null, null)
    }

    fun closeDb() {
        db.close()
    }
}