package com.survivalcoding.todolist.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

const val TODO_ITEM = "todoItem"
@SuppressLint("SimpleDateFormat")
fun convertToDate(time: Long): String = SimpleDateFormat("yyyy-MM-DD HH:mm:ss").format(Date(time))
