package com.survivalcoding.todolist.view.main.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.survivalcoding.todolist.R
import com.survivalcoding.todolist.databinding.ListItemBinding
import com.survivalcoding.todolist.view.main.model.TodoData
import java.text.SimpleDateFormat
import java.util.*

class TodoRecyclerViewHolder(view: View, val binding: ListItemBinding) :
    RecyclerView.ViewHolder(view) {
    fun bind(item: TodoData, sortFunction: (Int) -> Unit) {
        binding.checkBox.setOnClickListener {
            // To-Do 항목 완료
            item.isChecked = !item.isChecked
            sortFunction.invoke(item.pid)
        }
        binding.markBox.setOnClickListener {
            // To-Do 항목 즐겨찾기
            item.isMarked = !item.isMarked
            if (item.isMarked) binding.markBox.setImageResource(R.drawable.ic_baseline_star_24)
            else binding.markBox.setImageResource(R.drawable.ic_baseline_star_outline_24)
            sortFunction.invoke(item.pid)
        }
        binding.textTodo.text = item.text
        if (item.isMarked) binding.markBox.setImageResource(R.drawable.ic_baseline_star_24)
        else binding.markBox.setImageResource(R.drawable.ic_baseline_star_outline_24)
        binding.checkBox.isChecked = item.isChecked
        val dateFormat =
            SimpleDateFormat("yyyy.MM.dd HH:mm:ss", Locale.KOREA).format(Date(item.time))
        binding.textDate.text = dateFormat.toString()
    }
}