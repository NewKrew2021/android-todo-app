package com.survivalcoding.todolist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.survivalcoding.todolist.R
import com.survivalcoding.todolist.view.TodoData

class TodoRecyclerViewAdapter() :
    RecyclerView.Adapter<TodoRecyclerViewHolder>() {

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
        //To-Do 아이템 추
        items.add(data)
        sortItem()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): TodoRecyclerViewHolder {

        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.list_item, viewGroup, false)

        return TodoRecyclerViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: TodoRecyclerViewHolder, position: Int) {

        val item = items[position]
        viewHolder.checkBox.setOnClickListener {
            // To-Do 항목 완료
            item.check = !item.check
            sortItem()
        }
        viewHolder.markBox.setOnClickListener {
            // To-Do 항목 즐겨찾기
            item.mark = !item.mark
            if (item.mark) viewHolder.markBox.setImageResource(R.drawable.ic_baseline_star_24)
            else viewHolder.markBox.setImageResource(R.drawable.ic_baseline_star_outline_24)
            sortItem()
        }
        viewHolder.textTodo.text = item.text
        if (item.mark) viewHolder.markBox.setImageResource(R.drawable.ic_baseline_star_24)
        else viewHolder.markBox.setImageResource(R.drawable.ic_baseline_star_outline_24)
        viewHolder.checkBox.isChecked = item.check

    }

    override fun getItemCount() = items.size

}