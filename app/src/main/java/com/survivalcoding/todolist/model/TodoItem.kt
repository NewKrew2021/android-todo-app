package com.survivalcoding.todolist.model

import android.os.Parcelable

@kotlinx.android.parcel.Parcelize
data class TodoItem(
    val title: String,
    var checked: Boolean,
    var timeStamp: Long,
) : Parcelable