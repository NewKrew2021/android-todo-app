package com.survivalcoding.todolist.util

import java.text.SimpleDateFormat
import java.util.*

const val DATE_FORMAT_YYYY_MM_DD_HH_MM_SS = "yyyy/MM/dd HH:mm:ss"

fun getCurrentTime(): String = SimpleDateFormat(
    DATE_FORMAT_YYYY_MM_DD_HH_MM_SS,
    Locale.KOREA
).format(Date(System.currentTimeMillis()))