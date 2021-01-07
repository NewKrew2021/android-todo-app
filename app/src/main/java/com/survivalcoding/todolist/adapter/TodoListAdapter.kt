package com.survivalcoding.todolist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.survivalcoding.todolist.databinding.ToDoListBinding
import com.survivalcoding.todolist.model.TodoItem

class TodoListAdapter(private val list: MutableList<TodoItem>) :
    RecyclerView.Adapter<TodoListAdapter.TodoViewHolder>() {
    private lateinit var binding: ToDoListBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        binding = ToDoListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.apply {
            bind(list[position])
            setOnClickListener(position)
        }
    }

    override fun getItemCount() = list.size

    fun addNewTodo(item: TodoItem) {
        list.add(item)
        notifyItemInserted(list.size)
    }

    private fun removeTodo(position: Int) {
        list.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, list.size)
    }

    private fun setOnClickListener(position: Int) {
        binding.apply {
            checkBox.setOnClickListener {
                list[position].isChecked = checkBox.isChecked
            }
            toDoTitle.setOnClickListener {
                checkBox.isChecked.apply {
                    list[position].isChecked = !this
                    checkBox.isChecked = !this
                }
            }
            removeButton.setOnClickListener {
                removeTodo(position)
            }
        }
    }

    class TodoViewHolder(private val binding: ToDoListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(todoItem: TodoItem) {
            binding.apply {
                checkBox.isChecked = todoItem.isChecked
                toDoTitle.text = todoItem.todoTitle
            }
        }
    }
}