package com.survivalcoding.todolist.todo.data

import com.survivalcoding.todolist.todo.view.MainActivity
import com.survivalcoding.todolist.todo.view.model.Todo
import java.util.concurrent.atomic.AtomicInteger

// TodoList에 해당하는 데이터가 존재하는 클래스
// 데이터의 삽입, 삭제, 수정, 정렬을 위한 메소드가 존재한다.
class TodoData(var data: MutableList<Todo> = mutableListOf()) {
    private val id = AtomicInteger(0)

    val todoList: List<Todo>    // getter
        get() = data

    fun addTodo(item: Todo) {   // 데이터 추가
        item.id = id.getAndIncrement()
        data.add(item)
    }

    fun deleteTodo(item: Todo) {    // 데이터 삭제
        data.remove(item)
    }

    fun updateTodo(item: Todo) {    // 데이터 수정, todo완료시에 사용
        val updatedData = data.map {
            if (it.id == item.id) {
                item    // 수정하려는 item이면 해당 item을 넣는다.
            } else {
                it
            }
        }
        data.apply {
            clear()
            addAll(updatedData)
        }
    }

    fun updateTodo(_item: List<Todo>) {
        data.clear()
        data.addAll(_item)
    }

    // sortingBase : 정렬 기준(제목..), order : 오름/내림차순 정렬
    fun sorting(sortingBase: Int, order: Int) {
        when (sortingBase + order) {
            MainActivity.SORT_BY_TITLE + MainActivity.ASCENDING -> data.sortBy { it.text }
            MainActivity.SORT_BY_TITLE + MainActivity.DESCENDING -> data.sortByDescending { it.text }
            MainActivity.SORT_BY_D_DAY + MainActivity.ASCENDING -> data.sortBy { it.dueDate }
            MainActivity.SORT_BY_D_DAY + MainActivity.DESCENDING -> data.sortByDescending { it.dueDate }
        }
    }
}