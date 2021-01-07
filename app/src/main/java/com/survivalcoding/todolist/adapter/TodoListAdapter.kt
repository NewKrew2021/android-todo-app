package com.survivalcoding.todolist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.survivalcoding.todolist.databinding.TodoItemBinding
import com.survivalcoding.todolist.model.TodoItem


class TodoListAdapter : RecyclerView.Adapter<TodoListAdapter.TodoListViewHolder>() {
    private val items = listOf<TodoItem>(
            TodoItem("1순위", "d-20", false),
            TodoItem("2순위", "d-30", false),
            TodoItem("3순위", "d-32", false),
            TodoItem("4순위", "d-33", false),
            TodoItem("5순위", "d-34", false),
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoListViewHolder {
        val binding = TodoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodoListViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: TodoListViewHolder, position: Int) {
        val currentItem = items[position]
        holder.binding.apply {
            title.text = currentItem.title
            dDay.text = currentItem.date
            completeCheck.isChecked = currentItem.isComplete
            completeCheck.setOnClickListener { currentItem.isComplete = completeCheck.isChecked }
        }


    }

    class TodoListViewHolder(val binding: TodoItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }
}

