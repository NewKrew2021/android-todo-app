package com.survivalcoding.todolist.todo.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TodoItems(val isDone: Boolean, val title: String) : Parcelable