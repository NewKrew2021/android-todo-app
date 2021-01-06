package com.survivalcoding.todolist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.survivalcoding.todolist.databinding.ListItemBinding
import com.survivalcoding.todolist.view.TodoData

class CustomAdapter() : BaseAdapter() {

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
        val holder: TodoListHolder
        if (convertView == null) {
            val binding =
                ListItemBinding.inflate(LayoutInflater.from(parent!!.context), parent, false)
            view = binding.root
            holder = TodoListHolder(binding)
            view.tag = holder
        } else {
            view = convertView
            holder = view.tag as TodoListHolder
        }
        val item = getItem(position)
//        holder.binding.checkBox.setOnClickListener {
//            // To-Do 항목 완료
//            item.check = !item.check
//            sortItem()
//        }
//        holder.binding.markBox.setOnClickListener {
//            // To-Do 항목 즐겨찾기
//            item.mark = !item.mark
//            if (item.mark) holder.binding.markBox.setImageResource(R.drawable.ic_baseline_star_24)
//            else holder.binding.markBox.setImageResource(R.drawable.ic_baseline_star_outline_24)
//            sortItem()
//        }
//        holder.binding.textTodo.text = item.text
//        if (item.mark) holder.binding.markBox.setImageResource(R.drawable.ic_baseline_star_24)
//        else holder.binding.markBox.setImageResource(R.drawable.ic_baseline_star_outline_24)
//        holder.binding.checkBox.isChecked = item.check
        holder.bind(item, ::sortItem)
        return view

    }
}
