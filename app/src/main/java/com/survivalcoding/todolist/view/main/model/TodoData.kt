package com.survivalcoding.todolist.view.main.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TodoData(
    var text: String,
    var time: Long,
    var isChecked: Boolean = false,
    var isMarked: Boolean = false,
    var isEdit: Boolean = false,
    var pid: Int = 0,
) : Parcelable