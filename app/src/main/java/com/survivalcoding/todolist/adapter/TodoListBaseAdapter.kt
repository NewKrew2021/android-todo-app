/*
package com.survivalcoding.todolist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.survivalcoding.todolist.databinding.ToDoListBinding
import com.survivalcoding.todolist.model.TodoItem

class TodoListBaseAdapter(private var list: MutableList<TodoItem>) : BaseAdapter() {
    override fun getCount() = list.size

    override fun getItem(position: Int) = list[position]

    override fun getItemId(position: Int) = list[position].hashCode().toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val holder: TodoViewHolder

        // 뷰홀더 패턴 적용
        if (convertView == null) {
            ToDoListBinding.inflate(LayoutInflater.from(parent?.context)).apply {
                holder = TodoViewHolder(this)
                view = root
                view.tag = holder
            }
        } else {
            view = convertView
            holder = view.tag as TodoViewHolder
        }
        holder.bind(list[position])
        holder.setOnClickListener(list[position])
        return view
    }

    fun addNewTodo(item: TodoItem) {
        list.add(item)
        notifyDataSetChanged()
    }

    class TodoViewHolder(private val binding: ToDoListBinding) {
        fun bind(todoItem: TodoItem) {
            todoItem.apply {
                binding.checkBox.isChecked = isChecked
                binding.toDoTitle.text = todoTitle
            }
        }

        fun setOnClickListener(todoItem: TodoItem) {
            binding.apply {
                checkBox.setOnClickListener {
                    todoItem.isChecked = checkBox.isChecked
                }
                toDoTitle.setOnClickListener {
                    checkBox.isChecked.apply {
                        todoItem.isChecked = !this
                        checkBox.isChecked = !this
                    }
                }
            }
        }
    }
}*/
