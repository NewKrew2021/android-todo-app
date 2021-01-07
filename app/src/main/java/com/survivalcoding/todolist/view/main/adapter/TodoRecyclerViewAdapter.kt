package com.survivalcoding.todolist.view.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.survivalcoding.todolist.databinding.ListItemBinding
import com.survivalcoding.todolist.view.main.model.TodoData


class TodoRecyclerViewAdapter() :
    RecyclerView.Adapter<TodoRecyclerViewHolder>() {

    private var _items = mutableListOf<TodoData>()
    val items: List<TodoData>
        get() = _items
    lateinit var holder: TodoRecyclerViewHolder

    fun addAllItems(data: List<TodoData>) {
        _items.clear()
        _items.addAll(data)
    }

    private fun sortItem(target: Int) {
        //To-Do 아이템 sorting (완료 -> 즐겨찾기 -> 시간 순으로)
        val fromPosition = _items.map { it.pid }.indexOf(target)
        _items = _items.sortedWith(compareBy(
            { if (it.isChecked) 1 else 0 },
            { if (it.isMarked) 0 else 1 },
            { -it.time }
        )).toMutableList()
        val toPosition = _items.map { it.pid }.indexOf(target)
        //이동 애니메이션 적용
        notifyItemMoved(fromPosition, toPosition)

    }

    fun addItem(data: TodoData) {
        //To-Do 아이템 추가
        _items.add(0, data)
        //추가 애니메이션 적용
        notifyItemInserted(0)
    }

    fun delItem(data: TodoData) {
        val delPosition = _items.indexOf(data)
        _items.remove(data)
        notifyItemRemoved(delPosition)
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