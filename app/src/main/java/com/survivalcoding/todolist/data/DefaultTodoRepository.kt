package com.survivalcoding.todolist.data

import com.survivalcoding.todolist.viewModel.listItem
import com.survivalcoding.todolist.viewModel.searchItem

interface DefaultTodoRepository {

    fun getDataList(): MutableList<listItem>

    fun getSearchDataList(): MutableList<searchItem>

    fun addItem(listitem: listItem)

    fun removeItem(id: Int)

    fun updateItem(listitem: listItem)

}