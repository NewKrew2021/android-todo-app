package com.survivalcoding.todolist.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TodoItem(val title: String, val date: String, var isComplete: Boolean, var isMark: Boolean) : Parcelable