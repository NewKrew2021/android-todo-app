package com.todolist.sqlite.ui.viewmodel

import android.os.AsyncTask
import androidx.lifecycle.ViewModel
import com.todolist.sqlite.data.model.TodoItem
import com.todolist.sqlite.data.repository.TodoRepoImpl

class MainViewModel(private val todoRepository: TodoRepoImpl) : ViewModel() {

    private var _todoList = mutableListOf<TodoItem>()
    val todoList: List<TodoItem>
        get() = _todoList

    fun getTodoList() {
        _todoList = AsyncTaskGetTodoList().execute(todoRepository).get() as MutableList<TodoItem>
    }

    fun addTodoItem(todoItem: TodoItem) {
        AsyncTaskTodoItem().execute(todoRepository, todoItem, TASK_ADD_TODO_ITEM)
    }

    fun removeTodoItem(todoItem: TodoItem) {
        AsyncTaskTodoItem().execute(todoRepository, todoItem, TASK_REMOVE_TODO_ITEM)
    }

    fun updateTodoItem(todoItem: TodoItem) {
        AsyncTaskTodoItem().execute(todoRepository, todoItem, TASK_UPDATE_TODO_ITEM)
    }

    class AsyncTaskGetTodoList: AsyncTask<Any, Unit, List<TodoItem>>() {
        override fun doInBackground(vararg params: Any?): List<TodoItem>? {
            val repository = params[0] as TodoRepoImpl
            return repository.getAllTodoItem()
        }
    }

    class AsyncTaskTodoItem : AsyncTask<Any, Unit, Unit>() {
        override fun doInBackground(vararg params: Any?) {
            val repository = params[0] as TodoRepoImpl
            val todoItem = params[1] as TodoItem
            when(params[2] as String) {
                TASK_ADD_TODO_ITEM -> repository.addTodo(todoItem)
                TASK_REMOVE_TODO_ITEM -> repository.removeTodo(todoItem)
                TASK_UPDATE_TODO_ITEM -> repository.updateTodo(todoItem)
            }
        }
    }

    companion object {
        const val TASK_ADD_TODO_ITEM = "add"
        const val TASK_REMOVE_TODO_ITEM = "remove"
        const val TASK_UPDATE_TODO_ITEM = "update"
    }

}