package com.survivalcoding.todolist.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.survivalcoding.todolist.R

class TodoListAdapter(
    private val context: Context,
    private val list: MutableList<TodoItem>
) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val view: View
        val holder: TodoViewHolder

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_todo, parent, false)

            holder = TodoViewHolder()
            holder.title = view.findViewById(R.id.title)
            holder.checkBox = view.findViewById(R.id.checkbox)

            view.tag = holder
        } else {
            view = convertView
            holder = view.tag as TodoViewHolder
        }

        val todoItem = getItem(position)

        holder.title.text = todoItem.title
        holder.checkBox.isChecked = todoItem.checked

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