package com.survivalcoding.todolist.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class TodoItem(
        val title: String,
        val date: Calendar,
        var isComplete: Boolean,
        var isMark: Boolean,
        var id: Int?) : Parcelable {
    constructor(title: String, date: Calendar, isComplete: Boolean, isMark: Boolean) : this(title, date, isComplete, isMark, null)

}

