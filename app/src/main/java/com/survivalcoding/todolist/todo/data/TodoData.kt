package com.survivalcoding.todolist.todo.data

import com.survivalcoding.todolist.todo.view.MainActivity
import com.survivalcoding.todolist.todo.view.OrderMethod
import com.survivalcoding.todolist.todo.view.SortingBase
import com.survivalcoding.todolist.todo.view.model.Todo
import java.util.*
import java.util.concurrent.atomic.AtomicInteger

class TodoData(private val data: MutableList<Todo> = mutableListOf()) : DefaultTodoData {
    private val id = AtomicInteger(0)

    override fun addTodo(item: Todo) {   // 데이터 추가
        item.id = id.getAndIncrement()
        data.add(item)
    }

    override fun deleteTodo(item: Todo) {    // 데이터 삭제
        data.remove(item)
    }

    override fun updateTodo(item: Todo) {    // 데이터 수정, todo완료시에 사용
        val updatedData = data.map {
            if (it.id == item.id) {
                item
            } else {
                it
            }
        }
        data.apply {
            clear()
            addAll(updatedData)
        }
    }

    // sortingBase : 정렬 기준(제목..), orderMethod : 오름/내림차순 정렬
    override fun sorting(
        sortingBase: SortingBase,
        orderMethod: OrderMethod,
        updateUI: (List<Todo>) -> Unit
    ) {
        when (sortingBase.value + orderMethod.value) {
            MainActivity.SORT_BY_TITLE + MainActivity.ASCENDING -> data.sortBy { it.text }
            MainActivity.SORT_BY_TITLE + MainActivity.DESCENDING -> data.sortByDescending { it.text }
            MainActivity.SORT_BY_D_DAY + MainActivity.ASCENDING -> data.sortBy { it.dueDate }
            MainActivity.SORT_BY_D_DAY + MainActivity.DESCENDING -> data.sortByDescending { it.dueDate }
        }
        updateUI.invoke(data)
    }

    override fun search(title: String, updateUI: (List<Todo>) -> Unit) {
        val result = data.filter {
            it.text.toLowerCase(Locale.ROOT).contains(title.toLowerCase(Locale.ROOT))
        }
        updateUI.invoke(result)
    }
}