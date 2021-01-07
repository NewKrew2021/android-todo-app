package com.survivalcoding.todolist.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

const val ADD_TODO_REQUEST_CODE = 1000

@SuppressLint("SimpleDateFormat")
fun convertToDate(time: Long): String = SimpleDateFormat("yyyy-MM-DD HH:mm:ss").format(Date(time))
