package com.survivalcoding.todolist.todo.view.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Todo(var isDone: Boolean = false, val text: String, val dueDate: Long, var id: Int = 0) :
    Parcelable