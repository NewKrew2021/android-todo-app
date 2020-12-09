package com.survivalcoding.todolist.view.main.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Todo(
    var todo: String,
    var datetime: Long,
    var isDone: Boolean = false,
    var id: Int = 0,
) : Parcelable
