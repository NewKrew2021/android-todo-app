package com.survivalcoding.todolist.viewModel

import android.os.Parcelable


@kotlinx.android.parcel.Parcelize
data class listItem(
    var toDo: String,
    var time: String,
    var check: Boolean,
    var complete: Boolean,
    var id: Int
) : Parcelable