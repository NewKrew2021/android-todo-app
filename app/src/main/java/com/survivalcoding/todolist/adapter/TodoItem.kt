package com.survivalcoding.todolist.adapter

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TodoItem(val title: String, var checked: Boolean, var timeStamp: Long): Parcelable