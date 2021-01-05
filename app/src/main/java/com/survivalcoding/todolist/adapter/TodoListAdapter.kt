package com.survivalcoding.todolist.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.TextView
import com.survivalcoding.todolist.R

class TodoListAdapter(
    private val context: Context,
    private val list: MutableList<TodoItem>
) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val view =
            convertView ?: LayoutInflater.from(context).inflate(R.layout.item_todo, parent, false)

        val title = view.findViewById<TextView>(R.id.title)
        val checkBox = view.findViewById<CheckBox>(R.id.checkbox)

        val todoItem = getItem(position)

        title.text = todoItem.title
        checkBox.isChecked = todoItem.checked

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