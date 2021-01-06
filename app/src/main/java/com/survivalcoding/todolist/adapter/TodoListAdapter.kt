package com.survivalcoding.todolist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.survivalcoding.todolist.databinding.TodoItemBinding
import com.survivalcoding.todolist.model.TodoItem


class TodoListAdapter : RecyclerView.Adapter<TodoListAdapter.TodoListViewHolder>() {
    private val items = listOf<TodoItem>(
            TodoItem("1순위", "d-20"),
            TodoItem("2순위", "d-30"),
            TodoItem("3순위", "d-32"),
            TodoItem("4순위", "d-33"),
            TodoItem("5순위", "d-34"),
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
        }
    }

    class TodoListViewHolder(val binding: TodoItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }
}

