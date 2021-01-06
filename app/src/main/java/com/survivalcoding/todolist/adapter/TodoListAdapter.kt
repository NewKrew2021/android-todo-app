package com.survivalcoding.todolist.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.survivalcoding.todolist.databinding.ItemTodoBinding

class TodoListAdapter(
    private val context: Context
) : RecyclerView.Adapter<TodoViewHolder>() {

    private val list = mutableListOf<TodoItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val binding = ItemTodoBinding.inflate(LayoutInflater.from(context), parent, false)
        return TodoViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(list[position], ::sortItems)
    }

    fun addItem(item: TodoItem) {
        list.add(0, item)
        notifyItemInserted(0)
        notifyItemRangeChanged(0, list.size)
    }

    private fun sortItems() {
        list.sortWith(compareBy({ it.checked }, { -it.timeStamp }))
        notifyDataSetChanged()
    }
}