package com.survivalcoding.todolist.view.main.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TodoData(
    var text: String,
    var time: Long = 0L,
    var isDone: Boolean = false,
    var pid: Int = 0,
) : Parcelable