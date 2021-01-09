package com.survivalcoding.todolist.view.main.adapter

import androidx.recyclerview.widget.DiffUtil
import com.survivalcoding.todolist.view.main.model.Todo

object TodoDiffCallback : DiffUtil.ItemCallback<Todo>() {
    override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
        return oldItem == newItem
    }
}