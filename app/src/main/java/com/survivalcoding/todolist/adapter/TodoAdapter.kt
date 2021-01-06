package com.survivalcoding.todolist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.survivalcoding.todolist.databinding.ItemTodoBinding
import com.survivalcoding.todolist.model.TodoItem

class TodoAdapter(private val todoList: MutableList<TodoItem>) :
    RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemTodoBinding = ItemTodoBinding.inflate(inflater, parent, false)
        return TodoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bindView(todoList[position])
    }

    override fun getItemCount() = todoList.size

    fun addTodoItem(todoItem: TodoItem) = todoList.add(todoItem)

    class TodoViewHolder(private val binding: ItemTodoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindView(todoItem: TodoItem) {
            binding.apply {
                cbCompleteTodo.isChecked = todoItem.complete
                tvTimeTodo.text = todoItem.time
                tvContentsTodo.text = todoItem.contents
            }
        }

    }
}

