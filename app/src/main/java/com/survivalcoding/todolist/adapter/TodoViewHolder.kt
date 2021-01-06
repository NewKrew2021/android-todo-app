package com.survivalcoding.todolist.adapter

import com.survivalcoding.todolist.databinding.ItemTodoBinding

class TodoViewHolder(private val binding: ItemTodoBinding) {

    fun bind(todoItem: TodoItem) {
        binding.title.text = todoItem.title
        binding.checkbox.isChecked = todoItem.checked
    }
}