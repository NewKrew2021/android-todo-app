package com.survivalcoding.todolist.view.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.survivalcoding.todolist.R
import com.survivalcoding.todolist.databinding.ItemTodoBinding
import com.survivalcoding.todolist.view.main.holder.TodoViewHolder
import com.survivalcoding.todolist.view.main.model.Todo
import java.text.SimpleDateFormat
import java.util.*

class TodoRecyclerAdapter(private val clickListener: (todo: Todo) -> Unit) :
    ListAdapter<Todo, TodoViewHolder>(TodoDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_todo, parent, false)
        val binding = ItemTodoBinding.bind(view)
        val holder = TodoViewHolder(binding)

        binding.root.setOnClickListener {
            clickListener.invoke(getItem(holder.adapterPosition))
        }
        return holder
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val todo = getItem(position)

        val dateFormat = SimpleDateFormat("yyyy. MM. dd a hh:mm:ss", Locale.getDefault())

        holder.binding.todoText.text = todo.todo
        holder.binding.timeText.text = dateFormat.format(todo.datetime)
    }
}