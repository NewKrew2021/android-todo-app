package com.survivalcoding.todolist.adapter

import androidx.recyclerview.widget.RecyclerView
import com.survivalcoding.todolist.databinding.ItemTodoBinding
import com.survivalcoding.todolist.model.TodoItem

class TodoViewHolder(
    private val binding: ItemTodoBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: TodoItem, sort: () -> Unit, remove: (TodoItem) -> Unit, update: () -> Unit) {
        binding.apply {
            title.text = item.title
            checkbox.isChecked = item.checked

            checkbox.setOnClickListener {
                item.checked = item.checked xor true
                sort()
                update()
            }

            delete.setOnClickListener {
                remove(item)
                update()
            }
        }
    }

}