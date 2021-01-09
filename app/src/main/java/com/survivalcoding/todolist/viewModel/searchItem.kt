package com.survivalcoding.todolist.viewModel

import android.os.Parcelable

@kotlinx.android.parcel.Parcelize
data class searchItem(var item : listItem, var index: Int) :
    Parcelable