package com.survivalcoding.todolist.todo.adapter

import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView

data class Todos(val isDone: Boolean, val Title: String)

class TodoViewHolder {
    lateinit var isDone: RadioButton
    lateinit var titleText: TextView
    lateinit var editTitle: ImageView
}