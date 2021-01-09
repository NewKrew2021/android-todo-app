package com.survivalcoding.todolist.view

import androidx.recyclerview.widget.DiffUtil
import com.survivalcoding.todolist.viewModel.listItem

object ItemDiffCallback : DiffUtil.ItemCallback<listItem>() {
    override fun areItemsTheSame(oldItem: listItem, newItem: listItem): Boolean {
        return oldItem.time==newItem.time

    }

    override fun areContentsTheSame(oldItem: listItem, newItem: listItem): Boolean {
        return oldItem==newItem
    }

}