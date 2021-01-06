package com.survivalcoding.todolist.adapter

import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.survivalcoding.todolist.R

class TodoRecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val textTodo: TextView
    val checkBox: CheckBox
    val markBox: ImageView


    init {
        textTodo = view.findViewById(R.id.text_todo)
        checkBox = view.findViewById(R.id.check_box)
        markBox = view.findViewById(R.id.mark_box)


    }
}