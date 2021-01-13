package com.survivalcoding.todolist.data

import com.survivalcoding.todolist.view.main.model.TodoData
import java.util.*
import java.util.concurrent.atomic.AtomicInteger

class TodoLocalRepository : TodoDefaultRepository {
    private var _items = mutableListOf<TodoData>()
    private var pid = AtomicInteger(0)
    val items: List<TodoData>
        get() = _items

    override fun addItem(data: TodoData) {
        //To-Do 아이템 추가
        data.pid = pid.getAndIncrement()
        data.time = Calendar.getInstance().timeInMillis
        _items.add(0, data)
    }

    override fun delItem(data: TodoData) {
        _items.remove(data)
    }

    override fun addAllItems(data: List<TodoData>) {
        _items.clear()
        _items.addAll(data)
    }

    override fun sortItem() {
        //To-Do 아이템 sorting (완료 -> 즐겨찾기 -> 시간 순으로)
        _items = _items.sortedWith(compareBy(
            { if (it.isDone) 1 else 0 },
            { if (it.isMarked) 0 else 1 },
            { -it.time }
        )).toMutableList()
    }
}