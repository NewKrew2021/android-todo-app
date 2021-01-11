package com.survivalcoding.todolist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.survivalcoding.todolist.databinding.TodoItemBinding
import com.survivalcoding.todolist.holder.TodoListViewHolder
import com.survivalcoding.todolist.model.TodoItem
import java.util.*


class TodoListAdapter(private val _clickListener: (todo: TodoItem) -> Unit) : ListAdapter<TodoItem, TodoListViewHolder>(TodoCallback) {
    val currentTime = Calendar.getInstance()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoListViewHolder {
        val binding = TodoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodoListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TodoListViewHolder, position: Int) {
        val currentItem = getItem(holder.adapterPosition)
        holder.binding.apply {
            title.text = currentItem.title
            val longDday = getDay(currentItem.date)
            val longTday = getDay(currentTime)
            if (longTday - longDday - 1 == 0L) {
                dDay.text = "D-0"
            } else if (longTday - longDday - 1 < 0) {
                dDay.text = "D${longTday - longDday - 1}"
            } else {
                dDay.text = "D+${longTday - longDday - 1}"
            }
            completeCheck.isChecked = currentItem.isComplete
            completeCheck.setOnClickListener { currentItem.isComplete = completeCheck.isChecked }
            modifyButton.setOnClickListener{
                _clickListener.invoke(getItem(holder.adapterPosition))
            }
        }
    }

    fun getDay(calendar: Calendar): Long = calendar.timeInMillis / (24 * 60 * 60 * 1000)



}

