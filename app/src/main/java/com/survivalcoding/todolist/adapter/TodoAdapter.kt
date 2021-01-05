package com.survivalcoding.todolist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class TodoAdapter : BaseAdapter() {

    private val items = listOf(
        "Hi", "Hello", "Why",
        "Hi", "Hello", "Why", "Hi", "Hello", "Why", "Hi", "Hello", "Why",
        "Hi", "Hello", "Why", "Hi", "Hello", "Why", "Hi", "Hello", "Why",
    )

    class TodoViewHolder {
        lateinit var tempText: TextView
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val holder: TodoViewHolder

        if (convertView == null) {
            view = LayoutInflater.from(parent!!.context)
                .inflate(android.R.layout.simple_list_item_1, parent, false)

            holder = TodoViewHolder()
            holder.tempText = view.findViewById(android.R.id.text1)

            view.tag = holder
        } else {
            view = convertView
            holder = (view.tag) as TodoViewHolder
        }

        holder.tempText.text = getItem(position)

        return view
    }

    override fun getItem(position: Int): String = items[position]

    override fun getItemId(position: Int): Long = 0L

    override fun getCount(): Int = items.size
}