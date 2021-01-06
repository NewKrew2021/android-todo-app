package com.survivalcoding.todolist.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.survivalcoding.todolist.databinding.TodoItemBinding
import com.survivalcoding.todolist.model.TodoItem


class TodoListAdapter : BaseAdapter() {
    private val items = listOf<TodoItem>(
            TodoItem("1순위", "d-20"),
            TodoItem("2순위", "d-30"),
            TodoItem("3순위", "d-32"),
            TodoItem("4순위", "d-33"),
            TodoItem("5순위", "d-34"),
    )

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val currentItem = getItem(position) as TodoItem
        val binding = TodoItemBinding.inflate(LayoutInflater.from(parent!!.context), parent, false)

        binding.apply {
            title.text = currentItem.title
            dDay.text = currentItem.date
        }
        return binding.root
    }

    override fun getItem(position: Int): Any = items[position]

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int = items.size

}