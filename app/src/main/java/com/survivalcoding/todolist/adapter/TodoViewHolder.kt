package com.survivalcoding.todolist.adapter

import androidx.recyclerview.widget.RecyclerView
import com.survivalcoding.todolist.databinding.ItemTodoBinding
import com.survivalcoding.todolist.model.TodoItem

class TodoViewHolder(
    private val binding: ItemTodoBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        item: TodoItem,
        remove: (TodoItem) -> Unit,
        updateUi: () -> Unit,
        updateItem: (TodoItem) -> Unit,
    ) {
        binding.apply {
            title.text = item.title
            checkbox.isChecked = item.isChecked

            checkbox.setOnClickListener {
                item.isChecked = item.isChecked xor true
                updateItem(item)
                updateUi()
            }

            delete.setOnClickListener {
                remove(item)
                updateUi()
            }
        }
    }

}