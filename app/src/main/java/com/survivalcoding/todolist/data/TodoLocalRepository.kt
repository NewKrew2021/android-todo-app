package com.survivalcoding.todolist.data

import com.survivalcoding.todolist.view.main.model.TodoData
import java.util.*
import java.util.concurrent.atomic.AtomicInteger

class TodoLocalRepository : TodoRepository {
    private var _items = mutableListOf<TodoData>()
    private var pid = AtomicInteger(0)
    override fun getItems(): List<TodoData> {
        return _items.sortedWith(compareBy(
            { if (it.isDone) 1 else 0 },
            { -it.time }
        )).toList()
    }

    override fun addItem(data: TodoData) {
        //To-Do 아이템 추가
        data.pid = pid.getAndIncrement()
        data.time = Calendar.getInstance().timeInMillis
        _items.add(0, data)
    }

    override fun delItem(data: TodoData) {
        _items.remove(data)
    }

    override fun editItem(data: TodoData, changeTodo: String) {
        val newData = data.copy()
        delItem(data)
        addItem(newData.apply { text = changeTodo })
    }


    override fun doneItem(data: TodoData) {
        data.isDone = !data.isDone
    }
}