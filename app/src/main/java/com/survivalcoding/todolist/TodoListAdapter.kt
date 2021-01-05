package com.survivalcoding.todolist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter

class TodoListAdapter(private var list: MutableList<TodoItem>) : BaseAdapter() {
    override fun getCount() = list.size

    override fun getItem(position: Int) = list[position]

    override fun getItemId(position: Int) = list[position].hashCode().toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val holder: TodoViewHolder

        // 뷰홀더 패턴 적용
        if (convertView == null) {
            holder = TodoViewHolder()
            view = LayoutInflater.from(parent?.context).inflate(R.layout.to_do_list, parent, false)
            holder.checkBox = view.findViewById(R.id.check_box)
            holder.todoTitle = view.findViewById(R.id.to_do_title)
            view.tag = holder
        } else {
            view = convertView
            holder = view.tag as TodoViewHolder
        }
        holder.checkBox.isChecked = list[position].isChecked
        holder.todoTitle.text = list[position].todoTitle
        setOnClickListener(holder, position)
        return view
    }

    fun addNewTodo(item: TodoItem) {
        list.add(item)
        notifyDataSetChanged()
    }

    private fun setOnClickListener(holder: TodoViewHolder, position: Int) {
        holder.checkBox.setOnClickListener {
            list[position].isChecked = holder.checkBox.isChecked
        }

        holder.todoTitle.setOnClickListener {
            list[position].isChecked = !holder.checkBox.isChecked
            holder.checkBox.isChecked = !holder.checkBox.isChecked
        }
    }
}