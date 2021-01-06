package com.survivalcoding.todolist.adapter

import androidx.recyclerview.widget.RecyclerView
import com.survivalcoding.todolist.databinding.ItemTodoBinding

class TodoViewHolder(
    private val binding: ItemTodoBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: TodoItem) {
        binding.title.text = item.title
        binding.checkbox.isChecked = item.checked
    }
}