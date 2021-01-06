package com.survivalcoding.todolist.adapter

import com.survivalcoding.todolist.databinding.ItemTodoBinding

class TodoViewHolder(val binding: ItemTodoBinding) {

    fun bind(item: TodoItem) {
        binding.title.text = item.title
        binding.checkbox.isChecked = item.checked
    }
}