package com.survivalcoding.todolist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.survivalcoding.todolist.databinding.ItemTodoBinding

class TodoListAdapter : RecyclerView.Adapter<TodoViewHolder>() {

    private val _list = mutableListOf<TodoItem>()
    val list: List<TodoItem>
        get() = _list

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val binding = ItemTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodoViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(list[position]) {
            sortItems()
        }
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

    private fun sortItems() {
        _list.sortWith(compareBy({ it.checked }, { -it.timeStamp }))
        notifyDataSetChanged()
    }
}