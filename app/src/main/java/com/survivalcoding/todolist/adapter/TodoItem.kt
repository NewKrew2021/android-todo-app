package com.survivalcoding.todolist.adapter

import android.os.Parcelable

@kotlinx.android.parcel.Parcelize
data class TodoItem(
    val title: String,
    var checked: Boolean,
    var timeStamp: Long,
) : Parcelable