package com.survivalcoding.todolist.todo.view.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TodoItem(val isDone: Boolean, val title: String) : Parcelable