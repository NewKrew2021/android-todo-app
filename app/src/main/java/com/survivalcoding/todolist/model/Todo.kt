package com.survivalcoding.todolist.model

import android.os.Parcelable

@kotlinx.android.parcel.Parcelize
data class Todo (
    var title: String,
    var times: String,
    var isDone: Boolean = false,
    var isOption: Boolean = false,  // true : 수정 삭제 Visible, false : 수정 삭제 Invisible
) : Parcelable