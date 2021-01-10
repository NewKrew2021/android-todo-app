package com.survivalcoding.todolist.view.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.survivalcoding.todolist.databinding.ListItemBinding
import com.survivalcoding.todolist.view.main.model.TodoData


class TodoRecyclerViewAdapter() :
    ListAdapter<TodoData, TodoRecyclerViewHolder>(TodoDiffCallback) {

    private var _items = mutableListOf<TodoData>()
    val items: List<TodoData>
        get() = _items
    lateinit var holder: TodoRecyclerViewHolder

    fun addAllItems(data: List<TodoData>) {
        _items.clear()
        _items.addAll(data)
    }

    private fun sortItem() {
        //To-Do 아이템 sorting (완료 -> 즐겨찾기 -> 시간 순으로)
        _items = _items.sortedWith(compareBy(
            { if (it.isChecked) 1 else 0 },
            { if (it.isMarked) 0 else 1 },
            { -it.time }
        )).toMutableList()
        //이동 애니메이션 적용
        submitList(_items.toList())
    }

    fun addItem(data: TodoData) {
        //To-Do 아이템 추가
        _items.add(0, data)
        //추가 애니메이션 적용
        submitList(_items.toList())
    }

    private fun delItem(data: TodoData) {
        _items.remove(data)
        submitList(_items.toList())
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): TodoRecyclerViewHolder {
        val binding =
            ListItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        val view = binding.root
        holder = TodoRecyclerViewHolder(view, binding)
        view.tag = holder
        return holder
    }

    override fun onBindViewHolder(viewHolder: TodoRecyclerViewHolder, position: Int) {

        val item = _items[position]
        holder.bind(item, ::sortItem, ::delItem)

    }

    override fun getItemCount() = _items.size

}