package com.survivalcoding.todolist.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TodoItem(
        val title: String,
        val date: Long,
        var isComplete: Boolean,
        var isMark: Boolean,
        var id: Int? = null) : Parcelable