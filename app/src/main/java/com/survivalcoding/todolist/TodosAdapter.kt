package com.survivalcoding.todolist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView

class TodosAdapter constructor(private val todos: List<Todos>) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val holder: TodoViewHolder

        if (convertView == null) {
            view = LayoutInflater.from(parent!!.context).inflate(R.layout.item_todo, parent, false)
            holder = TodoViewHolder()
            holder.isDone = view.findViewById<RadioButton>(R.id.isDoneButton)
            holder.titleText = view.findViewById<TextView>(R.id.title)
            holder.editTitle = view.findViewById<ImageView>(R.id.editTitle)
            view.tag = holder
        } else {
            view = convertView
            holder = view.tag as TodoViewHolder
        }

        val currentTodo = getItem(position)

        holder.isDone.isChecked = currentTodo.isDone
        holder.titleText.text = currentTodo.Title

        return view
    }

    override fun getItem(position: Int) = todos[position]

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount() = todos.size

}