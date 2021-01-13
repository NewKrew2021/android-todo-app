package com.survivalcoding.todolist.view.main.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.survivalcoding.todolist.databinding.ListItemBinding
import com.survivalcoding.todolist.view.main.model.TodoData
import java.text.SimpleDateFormat
import java.util.*

class TodoViewHolder(view: View, private val binding: ListItemBinding) :
    RecyclerView.ViewHolder(view) {
    fun bind(
        item: TodoData,
    ) {
        binding.textTodo.text = item.text
        binding.checkBox.isChecked = item.isDone
        val dateFormat =
            SimpleDateFormat("yyyy.MM.dd HH:mm:ss", Locale.KOREA).format(Date(item.time))
        binding.textDate.text = dateFormat.toString()

    }
}