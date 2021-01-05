package com.survivalcoding.todolist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

data class TodoItem(val title: String, val date: String)
class TodoListAdapter : BaseAdapter() {
    private val items = listOf<TodoItem>(
            TodoItem("1순위", "d-20"),
            TodoItem("2순위", "d-30"),
            TodoItem("3순위", "d-32"),
            TodoItem("4순위", "d-33"),
            TodoItem("5순위", "d-34"),
    )

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        if (convertView == null) {
            view = LayoutInflater.from(parent!!.context)
                    .inflate(R.layout.todo_item, parent, false)
        } else {
            view = convertView
        }

        val title = view.findViewById<TextView>(R.id.title)
        val dDay = view.findViewById<TextView>(R.id.dDay)
        val currentItem = getItem(position) as TodoItem

        title.text = currentItem.title
        dDay.text = currentItem.date

        return view
    }

    override fun getItem(position: Int): Any = items[position]

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int = items.size

}