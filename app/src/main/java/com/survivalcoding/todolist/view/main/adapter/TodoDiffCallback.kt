package com.survivalcoding.todolist.view.main.adapter

import androidx.recyclerview.widget.DiffUtil
import com.survivalcoding.todolist.view.main.model.TodoData

object TodoDiffCallback : DiffUtil.ItemCallback<TodoData>() {
    override fun areItemsTheSame(oldItem: TodoData, newItem: TodoData): Boolean {
        return oldItem.pid == newItem.pid
    }

    override fun areContentsTheSame(oldItem: TodoData, newItem: TodoData): Boolean {
        return oldItem == newItem
    }

}