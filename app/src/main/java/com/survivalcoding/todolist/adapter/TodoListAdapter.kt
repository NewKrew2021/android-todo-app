package com.survivalcoding.todolist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.survivalcoding.todolist.R
import com.survivalcoding.todolist.model.TodoItem

class TodoListAdapter(private val list: MutableList<TodoItem>) :
    RecyclerView.Adapter<TodoListAdapter.TodoViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.to_do_list, parent, false)
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.apply {
            bind(list[position])
            setOnClickListener(list[position])
        }
    }

    override fun getItemCount() = list.size

    fun addNewTodo(item: TodoItem) {
        list.add(item)
        notifyDataSetChanged()
    }

    class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val checkBox = itemView.findViewById<CheckBox>(R.id.check_box)
        private val title = itemView.findViewById<TextView>(R.id.to_do_title)

        fun bind(item: TodoItem) {
            checkBox.isChecked = item.isChecked
            title.text = item.todoTitle
        }

        fun setOnClickListener(item: TodoItem) {
            checkBox.setOnClickListener {
                item.isChecked = checkBox.isChecked
            }
            title.setOnClickListener {
                item.isChecked = !checkBox.isChecked
            }
        }
    }
}