package com.survivalcoding.todolist.model

import android.os.Parcelable

@kotlinx.android.parcel.Parcelize
data class TodoItem(
    var title: String,
    var isChecked: Boolean,
    var timeStamp: Long,
    var id: Int = 0,
) : Parcelable