package com.survivalcoding.todolist.adapter

import androidx.recyclerview.widget.DiffUtil
import com.survivalcoding.todolist.model.TodoItem

object TodoCallback : DiffUtil.ItemCallback<TodoItem>() {
    override fun areItemsTheSame(oldItem: TodoItem, newItem: TodoItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TodoItem, newItem: TodoItem): Boolean {
        return oldItem == newItem
    }


}
