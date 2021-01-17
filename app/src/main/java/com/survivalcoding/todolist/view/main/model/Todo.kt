package com.survivalcoding.todolist.view.main.model

import android.os.Parcelable

@kotlinx.android.parcel.Parcelize
data class Todo (
    var title: String,
    var times: Long,
    var isDone: Boolean = false,
    var isOption: Boolean = false,  // true : 수정 삭제 Visible, false : 수정 삭제 Invisible
    var isRemovable: Boolean = false, // true : 선택 삭제 목록에 포함, false : 포함되지 않음
    var id: Int = 0,
) : Parcelable {
    fun isEditable(): Boolean = isOption && !isDone // 수정하기 가능 true / 불가능 false
}