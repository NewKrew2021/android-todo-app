package com.survivalcoding.todolist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.survivalcoding.todolist.databinding.TodoItemBinding
import com.survivalcoding.todolist.holder.TodoListViewHolder
import com.survivalcoding.todolist.model.TodoItem
import java.util.*


class TodoListAdapter(private val _completeListener: (todo: TodoItem) -> Unit,
                      private val _modifyListener: (todo: TodoItem) -> Unit,
                      private val _deleteListener: (todo: TodoItem) -> Unit,
                      private val _markListener: (todo: TodoItem) -> Unit) : ListAdapter<TodoItem, TodoListViewHolder>(TodoCallback) {
    val dDayTime = Calendar.getInstance()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoListViewHolder {
        val binding = TodoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodoListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TodoListViewHolder, position: Int) {
        val currentItem = getItem(holder.adapterPosition)
        holder.binding.apply {
            title.text = currentItem.title
            dDayTime.time = Date(currentItem.date)
            val longDday = getDay(dDayTime)
            val longTday = getDay(Calendar.getInstance())
            if (longTday - longDday - 1 == 0L) {
                dDay.text = "D-0"
            } else if (longTday - longDday - 1 < 0) {
                dDay.text = "D${longTday - longDday - 1}"
            } else {
                dDay.text = "D+${longTday - longDday - 1}"
            }
            completeCheck.isChecked = currentItem.isComplete
            markButton.isChecked = currentItem.isMark
            modifyButton.setOnClickListener {
                _modifyListener.invoke(getItem(holder.adapterPosition))
            }
            deleteButton.setOnClickListener {
                _deleteListener.invoke(getItem(holder.adapterPosition))
            }
            completeCheck.setOnClickListener {
                _completeListener.invoke(getItem(holder.adapterPosition))
            }
            markButton.setOnClickListener {
                _markListener.invoke(getItem(holder.adapterPosition))
            }
        }
    }

    fun getDay(calendar: Calendar): Long = calendar.timeInMillis / (24 * 60 * 60 * 1000)


}

