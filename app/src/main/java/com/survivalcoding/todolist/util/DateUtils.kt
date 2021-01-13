package com.survivalcoding.todolist.util

import java.text.SimpleDateFormat

private val dateFormat = SimpleDateFormat("yyyy.MM.dd a hh:mm:ss")

fun timesToString(times: Long): String = dateFormat.format(times)