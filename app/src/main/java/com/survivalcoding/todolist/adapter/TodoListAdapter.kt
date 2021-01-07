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
        return TodoViewHolder(binding, ::removeTodo)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount() = list.size

    fun sortByTime() {
        list.sortBy { it.timeStamp }
        notifyDataSetChanged()
    }

    fun sortByTitle() {
        list.sortBy { it.todoTitle }
        notifyDataSetChanged()
    }

    fun addNewTodo(item: TodoItem) {
        list.add(item)
        notifyItemInserted(list.size)
    }

    private fun removeTodo(position: Int) {
        list.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, list.size)
    }

    class TodoViewHolder(
        private val binding: ToDoListBinding,
        private val removeItem: (Int) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(todoItem: TodoItem) {
            binding.apply {
                checkBox.isChecked = todoItem.isChecked
                toDoTitle.text = todoItem.todoTitle
                currentTime.text = todoItem.timeStamp
                setOnClickListener(todoItem)
            }
        }

        private fun setOnClickListener(item: TodoItem) {
            binding.apply {
                checkBox.setOnClickListener {
                    item.isChecked = checkBox.isChecked
                }
                toDoTitle.setOnClickListener {
                    checkBox.isChecked.apply {
                        item.isChecked = !this
                        checkBox.isChecked = !this
                    }
                }
                removeButton.setOnClickListener {
                    removeItem.invoke(adapterPosition)
                }
            }
        }
    }
}