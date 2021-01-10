package com.survivalcoding.todolist.todo.data

import com.survivalcoding.todolist.todo.view.main.MainActivity
import com.survivalcoding.todolist.todo.view.model.Todo

object TodoData {
    private val data = mutableListOf<Todo>(
        Todo(
            false,
            "Todo1",
            1610300000000L
        ),
        Todo(
            false,
            "Todo2",
            1610400000000L
        ),
        Todo(
            false,
            "Todo3",
            1610500000000L
        ),
        Todo(
            true,
            "Todo4",
            1610600000000L
        ),
    )

    val todoList: List<Todo>
        get() = data

    fun addTodo(item: Todo) {
        data.add(item)
    }

    fun updateTodo(item: ArrayList<Todo>) {
        data.clear()
        data.addAll(item.toMutableList())
    }

    fun deleteTodo(item: Todo) {
        data.remove(item)
    }

    fun doneTodo(pos: Int) {
        data[pos].isDone = !data[pos].isDone
    }

    fun sortByDate(order: Int) {
        if (order == MainActivity.ASCENDING) {
            data.sortBy { it.dueDate }
        } else if (order == MainActivity.DESCENDING) {
            data.sortByDescending { it.dueDate }
        }
    }

    fun sortByTitle(order: Int) {
        if (order == MainActivity.ASCENDING) {
            data.sortBy { it.text }
        } else if (order == MainActivity.DESCENDING) {
            data.sortByDescending { it.text }
        }
    }

    fun getDataSize(): Int {
        return data.size
    }
}