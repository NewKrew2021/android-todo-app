package com.survivalcoding.todolist.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TodoItem(
    var id: Int,
    var isChecked: Boolean,
    var todoTitle: String,
    var timeStamp: String
) : Parcelable