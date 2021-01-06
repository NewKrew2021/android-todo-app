package com.survivalcoding.todolist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.survivalcoding.todolist.databinding.ListItemBinding
import com.survivalcoding.todolist.view.TodoData

class TodoListViewAdapter() : BaseAdapter() {

    private var items = mutableListOf<TodoData>()

    private fun sortItem() {
        //To-Do 아이템 sorting (완료 -> 즐겨찾기 -> 시간 순으로)
        items = items.sortedWith(compareBy(
            { if (it.check) 1 else 0 },
            { if (it.mark) 0 else 1 },
            { -it.time }
        )).toMutableList()
        notifyDataSetChanged()
    }

    fun addItem(data: TodoData) {
        items.add(data)
        sortItem()
    }

    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(position: Int): TodoData {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val holder: TodoListViewHolder
        if (convertView == null) {
            val binding =
                ListItemBinding.inflate(LayoutInflater.from(parent!!.context), parent, false)
            view = binding.root
            holder = TodoListViewHolder(binding)
            view.tag = holder
        } else {
            view = convertView
            holder = view.tag as TodoListViewHolder
        }
        val item = getItem(position)
        holder.bind(item, ::sortItem)
        return view

    }
}
