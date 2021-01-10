package com.survivalcoding.todolist.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

const val ADD_TODO_REQUEST_CODE = 1000
const val TODO_ITEM_TIME = "time"
const val TODO_ITEM_CONTENTS = "contents"
const val TODO_ITEM = "todoItem"

@SuppressLint("SimpleDateFormat")
fun convertToDate(time: Long): String = SimpleDateFormat("yyyy-MM-DD HH:mm:ss").format(Date(time))
