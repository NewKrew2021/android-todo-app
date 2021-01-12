package com.survivalcoding.todolist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.survivalcoding.todolist.model.TodoItem

class SharedViewModel : ViewModel() {
    val shareData = MutableLiveData<TodoItem?>()
    val list = MutableLiveData<MutableList<TodoItem>>()

    fun getLiveData(): LiveData<TodoItem?> = shareData

    fun setLiveData(item: TodoItem?) {
        shareData.value = item
    }


}