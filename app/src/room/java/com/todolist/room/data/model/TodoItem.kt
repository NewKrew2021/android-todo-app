package com.todolist.room.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class TodoItem(
    @ColumnInfo(name = "time") var time: Long,
    @ColumnInfo(name = "contents") var contents: String?,
    @ColumnInfo(name = "complete") var complete: Boolean = false,
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
) : Parcelable