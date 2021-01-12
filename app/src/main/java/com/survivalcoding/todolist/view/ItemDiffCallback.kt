package com.survivalcoding.todolist.view

import androidx.recyclerview.widget.DiffUtil
import com.survivalcoding.todolist.viewModel.listItem
import com.survivalcoding.todolist.viewModel.searchItem

object ItemDiffCallback : DiffUtil.ItemCallback<searchItem>() {
    override fun areItemsTheSame(oldItem: searchItem, newItem: searchItem): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }

    override fun areContentsTheSame(oldItem: searchItem, newItem: searchItem): Boolean {
        return oldItem == newItem
    }
}