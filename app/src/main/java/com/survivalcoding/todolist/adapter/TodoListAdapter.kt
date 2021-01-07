package com.survivalcoding.todolist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.survivalcoding.todolist.databinding.ToDoListBinding
import com.survivalcoding.todolist.model.TodoItem

class TodoListAdapter(private val list: MutableList<TodoItem>) :
    RecyclerView.Adapter<TodoListAdapter.TodoViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(
            ToDoListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.apply {
            bind(list[position])
            setOnClickListener(list[position])
        }
    }

    override fun getItemCount() = list.size

    fun addNewTodo(item: TodoItem) {
        list.add(item)
        notifyDataSetChanged()
    }

    class TodoViewHolder(private val binding: ToDoListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(todoItem: TodoItem) {
            binding.apply {
                checkBox.isChecked = todoItem.isChecked
                toDoTitle.text = todoItem.todoTitle
            }
        }

        fun setOnClickListener(todoItem: TodoItem) {
            binding.apply {
                checkBox.setOnClickListener {
                    todoItem.isChecked = checkBox.isChecked
                }
                toDoTitle.setOnClickListener {
                    checkBox.isChecked.apply {
                        todoItem.isChecked = !this
                        checkBox.isChecked = !this
                    }
                }
            }
        }
    }
}