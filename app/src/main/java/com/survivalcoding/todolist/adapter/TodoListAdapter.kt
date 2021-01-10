package com.survivalcoding.todolist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.survivalcoding.todolist.databinding.ToDoListBinding
import com.survivalcoding.todolist.model.TodoItem

class TodoListAdapter(
    private val editTodoListener: (TodoItem, String) -> Unit,
    private val removeTodoListener: (TodoItem) -> Unit
) :
    ListAdapter<TodoItem, TodoListAdapter.TodoViewHolder>(TodoDiffUtilCallback()) {
    private lateinit var binding: ToDoListBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        binding = ToDoListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodoViewHolder(binding, editTodoListener, removeTodoListener)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class TodoViewHolder(
        private val binding: ToDoListBinding,
        private val editTodoListener: (TodoItem, String) -> Unit,
        private val removeTodoListener: (TodoItem) -> Unit
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
                editButton.setOnClickListener {
                    normalTodoLayout.visibility = View.INVISIBLE
                    editTodoLayout.visibility = View.VISIBLE
                    editTodoTitle.setText(toDoTitle.text.toString())
                }
                removeButton.setOnClickListener {
                    removeTodoListener(item)
                }
                editApplyButton.setOnClickListener {
                    normalTodoLayout.visibility = View.VISIBLE
                    editTodoLayout.visibility = View.INVISIBLE
                    toDoTitle.text = editTodoTitle.text.toString()
                    currentTime.text = item.timeStamp
                    editTodoListener(item, editTodoTitle.text.toString())
                }
                editCancelButton.setOnClickListener {
                    normalTodoLayout.visibility = View.VISIBLE
                    editTodoLayout.visibility = View.INVISIBLE
                }
            }
        }
    }
}
