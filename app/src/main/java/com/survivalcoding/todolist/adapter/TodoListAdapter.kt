package com.survivalcoding.todolist.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.survivalcoding.todolist.databinding.ItemTodoBinding

class TodoListAdapter(
    private val context: Context,
    private val list: MutableList<TodoItem>
) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val holder: TodoViewHolder

        if (convertView == null) {
            val binding = ItemTodoBinding.inflate(LayoutInflater.from(context), parent, false)
            view = binding.root

            holder = TodoViewHolder(binding)

            view.tag = holder
        } else {
            view = convertView
            holder = view.tag as TodoViewHolder
        }

        holder.bind(getItem(position))

        return view
    }

    override fun getItem(position: Int): TodoItem {
        return list[position]
    }

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount() = list.size

    fun addItem(item: TodoItem) {
        list.add(item)
        notifyDataSetChanged()
    }
}