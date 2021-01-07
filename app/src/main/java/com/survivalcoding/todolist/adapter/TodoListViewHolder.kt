package com.survivalcoding.todolist.adapter

import com.survivalcoding.todolist.R
import com.survivalcoding.todolist.databinding.ListItemBinding
import com.survivalcoding.todolist.view.TodoData

class TodoListViewHolder(val binding: ListItemBinding) {
    fun bind(item: TodoData, sortFunction: () -> Unit) {
        binding.checkBox.setOnClickListener {
            // To-Do 항목 완료
            item.isChecked = !item.isChecked
            sortFunction.invoke()
        }
        binding.markBox.setOnClickListener {
            // To-Do 항목 즐겨찾기
            item.isMarked = !item.isMarked
            if (item.isMarked) binding.markBox.setImageResource(R.drawable.ic_baseline_star_24)
            else binding.markBox.setImageResource(R.drawable.ic_baseline_star_outline_24)
            sortFunction.invoke()
        }
        binding.textTodo.text = item.text
        if (item.isMarked) binding.markBox.setImageResource(R.drawable.ic_baseline_star_24)
        else binding.markBox.setImageResource(R.drawable.ic_baseline_star_outline_24)
        binding.checkBox.isChecked = item.isChecked
    }
}