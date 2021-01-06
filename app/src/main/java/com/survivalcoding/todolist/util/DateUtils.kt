package com.survivalcoding.todolist.util

import java.text.SimpleDateFormat
import java.util.*

private val dateFormat = SimpleDateFormat("yyyy.MM.dd a hh:mm:ss")

fun dateToString(date: Date): String = dateFormat.format(date)

fun stringToDate(str: String): Date = dateFormat.parse(str)