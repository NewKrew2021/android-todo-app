package com.survivalcoding.todolist.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TodoItem(
        var time: Long,
        var contents: String?,
        var complete: Boolean,
) : Parcelable