package com.survivalcoding.todolist.todo.data

import com.survivalcoding.todolist.todo.view.main.MainActivity
import com.survivalcoding.todolist.todo.view.model.Todo
import java.util.concurrent.atomic.AtomicInteger

object TodoData {
    private var data = mutableListOf<Todo>()
    private val id = AtomicInteger(0)

    val todoList: List<Todo>
        get() = data

    fun addTodo(item: Todo) {
        item.id = id.getAndIncrement()
        data.add(item)
    }

    fun updateTodo(item: ArrayList<Todo>) {
        val _list = item.toList()   // 이유는 모르겠지만, data.clear()시에 item도 clear가 되서 임시로 저장.
        data.clear()
        data.addAll(_list)
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