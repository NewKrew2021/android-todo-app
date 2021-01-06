package com.survivalcoding.todolist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.survivalcoding.todolist.R
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
        val view = LayoutInflater.from(parent.context).inflate(R.layout.todo_item, parent, false)
        return TodoListViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: TodoListViewHolder, position: Int) {
        val currentItem = items[position]
        holder.title.text = currentItem.title
        holder.dDay.text = currentItem.date
    }

    class TodoListViewHolder(todoItemView: View) : RecyclerView.ViewHolder(todoItemView) {
        val title = todoItemView.findViewById<TextView>(R.id.title)
        val dDay = todoItemView.findViewById<TextView>(R.id.dDay)

    }
}

