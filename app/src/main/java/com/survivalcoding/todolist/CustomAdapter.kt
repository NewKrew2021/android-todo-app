package com.survivalcoding.todolist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView

class CustomAdapter() : BaseAdapter() {

    var items = mutableListOf<TodoData>()

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
        if (convertView == null) {
            view = LayoutInflater.from(parent!!.context)
                .inflate(R.layout.list_item, parent, false)
        } else {
            view = convertView
        }
        val checkBox = view.findViewById<CheckBox>(R.id.checkbox)
        checkBox.setOnClickListener {
            // To-Do 항목 완료
            items[position].check = !items[position].check
            sortItem()
        }
        val markBox = view.findViewById<ImageView>(R.id.mark_box)
        markBox.setOnClickListener {
            // To-Do 항목 즐겨찾기
            items[position].mark = !items[position].mark
            if (items[position].mark) markBox.setImageResource(R.drawable.ic_baseline_star_24)
            else markBox.setImageResource(R.drawable.ic_baseline_star_outline_24)
            sortItem()
        }
        val item = getItem(position)
        val todoText = view.findViewById<TextView>(R.id.text_todo)
        todoText.text = item.text
        if (item.mark) markBox.setImageResource(R.drawable.ic_baseline_star_24)
        else markBox.setImageResource(R.drawable.ic_baseline_star_outline_24)
        checkBox.isChecked = item.check
        return view

    }
}
