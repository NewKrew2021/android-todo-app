package com.survivalcoding.todolist.viewmodel

import com.survivalcoding.todolist.view.main.model.TodoData

class TodoRepository {
    private var _items = mutableListOf<TodoData>()
    val items: List<TodoData>
        get() = _items

    fun addItem(data: TodoData) {
        //To-Do 아이템 추가
        _items.add(0, data)
        //추가 애니메이션 적용
    }

    fun delItem(data: TodoData) {
        _items.remove(data)
    }

    fun addAllItems(data: List<TodoData>) {
        _items.clear()
        _items.addAll(data)
    }

    fun sortItem() {
        //To-Do 아이템 sorting (완료 -> 즐겨찾기 -> 시간 순으로)
        _items = _items.sortedWith(compareBy(
            { if (it.isChecked) 1 else 0 },
            { if (it.isMarked) 0 else 1 },
            { -it.time }
        )).toMutableList()
    }

    fun getSize(): Int {
        return _items.size
    }

    fun getItem(position: Int): TodoData {
        return _items[position]
    }
}