package com.survivalcoding.todolist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.survivalcoding.todolist.databinding.ItemTodoBinding

//class TodoListAdapter : RecyclerView.Adapter<TodoViewHolder>() {
class TodoListAdapter : ListAdapter<TodoItem, TodoViewHolder>(TodoDiffCallback) {

    private val _list = mutableListOf<TodoItem>()
    val list: List<TodoItem>
        get() = _list

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val binding = ItemTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodoViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(list[position], ::sortItems, ::removeItem)
    }

    fun addItem(item: TodoItem) {
        _list.add(0, item)
        notifyItemInserted(0)
        notifyItemRangeChanged(0, list.size)
    }

    fun resetItems(items: MutableList<TodoItem>) {
        _list.clear()
        _list.addAll(items)
        notifyDataSetChanged()
    }

    private fun removeItem(target: Int) {
        target
            .takeIf { it in 0.._list.size }
            ?.let {
                _list.removeAt(it)
                notifyItemRemoved(it)
            }
    }

    private fun sortItems() {
        _list.sortWith(compareBy({ it.checked }, { -it.timeStamp }))
        notifyDataSetChanged()
    }
}