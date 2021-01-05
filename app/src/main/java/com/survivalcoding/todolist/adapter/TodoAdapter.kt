package com.survivalcoding.todolist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.TextView
import com.survivalcoding.todolist.R
import com.survivalcoding.todolist.model.Todo

class TodoAdapter(
        private val todoList: MutableList<Todo>,
) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val holder: ViewHolder

        if (convertView == null) {
            view = LayoutInflater.from(parent!!.context).inflate(R.layout.item_todo, parent, false)

            holder = ViewHolder().apply {
                cbComplete = view.findViewById(R.id.cb_complete_todo)
                tvTime = view.findViewById(R.id.tv_time_todo)
                tvTitle = view.findViewById(R.id.tv_title_todo)
            }

        } else {
            view = convertView
            holder = view.tag as ViewHolder
        }

        val currentTodo = getItem(position)

        // 값 설정
        holder.apply {
            cbComplete.isChecked = currentTodo.complete
            tvTime.text = currentTodo.time
            tvTitle.text = currentTodo.title
        }

        return view
    }

    override fun getItem(position: Int) = todoList[position]

    override fun getItemId(position: Int): Long = 0

    override fun getCount() = todoList.size

    inner class ViewHolder {
        lateinit var cbComplete: CheckBox
        lateinit var tvTime: TextView
        lateinit var tvTitle: TextView
    }
}